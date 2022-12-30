package si.fri.rso.invoicemicroservice.services.pdfs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;

import si.fri.rso.invoicemicroservice.lib.Invoice;
import si.fri.rso.invoicemicroservice.lib.InvoiceItem;
import si.fri.rso.invoicemicroservice.services.config.ServicesProperties;
import si.fri.rso.invoicemicroservice.services.files.MinioHandler;
import si.fri.rso.invoicemicroservice.services.templates.TemplateEngine;

import com.itextpdf.html2pdf.HtmlConverter;

import org.json.JSONObject;

@RequestScoped
public class PdfGenerator {
    @Inject
    private TemplateEngine templateEngine;

    @Inject
    private MinioHandler minioHandler;

    @Inject
    ServicesProperties servicesProperties;

    private Client httpClient;

    @PostConstruct
    private void init() {
        this.httpClient = ClientBuilder.newClient();
    }

    public void generate(Invoice invoice) {
        // TODO: Replace magic strings with data fetched from other microservicess
        Map<String, String> dataModel = new HashMap<>();
        dataModel.put("totalAmount", String.valueOf(invoice.getAmount()));
        dataModel.put("invoiceNum", String.valueOf(invoice.getId()));
        dataModel.put("createdAt", "date");
        dataModel.put("user", "Test user");
        dataModel.put("email", "testuser@gmail.com");
        dataModel.put("item", this.itemName(invoice.getInvoiceItems().get(0)));

        String invoiceHTML = this.templateEngine.getTemplateHTML("invoice.html", dataModel);
        try {
            /**
             * Uploading pdf as a byte stream to minio worked, but PDF was blank. It had all
             * the data but it seems that the encoding was not correct. Due to this a file
             * is now generated, uploaded to minio then deleted.
             */
            String invoiceFileName = "invoice-" + invoice.getId() + ".pdf";
            HtmlConverter.convertToPdf(invoiceHTML, new FileOutputStream(invoiceFileName));

            this.minioHandler.uploadFileToBucket(invoiceFileName);
            File file = new File(invoiceFileName);
            file.delete();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String itemName(InvoiceItem item) {
        try {
            String itemStringObject = this.httpClient
                    .target(servicesProperties.getItemsServiceHost() + "/v1/items/" + item.getItemId().toString())
                    .request().get(new GenericType<String>() {
                    });

            JSONObject itemJsonObject = new JSONObject(itemStringObject);

            return itemJsonObject.getString("name");
        } catch (WebApplicationException | ProcessingException e) {
            System.out.println(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }
}
