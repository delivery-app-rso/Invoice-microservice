package si.fri.rso.invoicemicroservice.services.pdfs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import si.fri.rso.invoicemicroservice.lib.Invoice;
import si.fri.rso.invoicemicroservice.lib.InvoiceDto;
import si.fri.rso.invoicemicroservice.services.config.ServicesProperties;
import si.fri.rso.invoicemicroservice.services.files.MinioHandler;
import si.fri.rso.invoicemicroservice.services.templates.TemplateEngine;

import com.itextpdf.html2pdf.HtmlConverter;

@RequestScoped
public class PdfGenerator {
    @Inject
    private TemplateEngine templateEngine;

    @Inject
    private MinioHandler minioHandler;

    @Inject
    ServicesProperties servicesProperties;

    public void generate(Invoice invoice, InvoiceDto invoiceDto) {
        Map<String, String> dataModel = new HashMap<>();
        dataModel.put("totalAmount", String.valueOf(invoice.getAmount()));
        dataModel.put("invoiceNum", String.valueOf(invoice.getId()));
        dataModel.put("createdAt", LocalDate.now().toString());
        dataModel.put("user", invoiceDto.getUserData().get("name"));
        dataModel.put("email", invoiceDto.getUserData().get("email"));
        dataModel.put("address", invoiceDto.getUserData().get("address"));

        dataModel.put("item", invoiceDto.getDeliveryData().get("item"));
        String invoiceHTML = this.templateEngine.getTemplateHTML("invoice.html", dataModel);

        try {
            /**
             * Uploading pdf as a byte stream to minio worked, but PDF was blank. It had all
             * the data but it seems that the encoding was not correct. Due to this a file
             * is now generated, uploaded to minio then deleted.
             */
            String invoiceFileName = invoice.getFilename() + ".pdf";
            HtmlConverter.convertToPdf(invoiceHTML, new FileOutputStream(invoiceFileName));

            this.minioHandler.uploadFileToBucket(invoiceFileName);
            File file = new File(invoiceFileName);
            file.delete();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
