package si.fri.rso.invoicemicroservice.services.pdfs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import si.fri.rso.invoicemicroservice.lib.Invoice;
import si.fri.rso.invoicemicroservice.services.files.MinioHandler;
import si.fri.rso.invoicemicroservice.services.templates.TemplateEngine;

import com.itextpdf.html2pdf.HtmlConverter;

@RequestScoped
public class PdfGenerator {
    @Inject
    private TemplateEngine templateEngine;

    @Inject
    private MinioHandler minioHandler;

    public void generate(Invoice invoice) {
        // TODO: Replace magic strings with data fetched from other microservicess
        Map<String, String> dataModel = new HashMap<>();
        System.out.println("am here");
        dataModel.put("amount", String.valueOf(invoice.getAmount()));
        dataModel.put("totalAmount", String.valueOf(invoice.getAmount()));
        dataModel.put("invoiceNum", String.valueOf(invoice.getId()));
        dataModel.put("createdAt", "date");
        dataModel.put("user", "Test user");
        dataModel.put("email", "testuser@gmail.com");
        dataModel.put("item", "Test item");
        System.out.println("tet");
        String invoiceHTML = this.templateEngine.getTemplateHTML("invoice.html", dataModel);
        try {
            /**
             * Uploading pdf as a byte stream to minio worked, but PDF was blank. It had all
             * the data but it seems that the encoding was not correct. Due to this a file
             * is now generated, uploaded to minio then deleted.
             */
            String invoiceFileName = "invoice-" + invoice.getId() + ".pdf";
            HtmlConverter.convertToPdf(invoiceHTML, new FileOutputStream(invoiceFileName));
            System.out.println("converted");

            this.minioHandler.uploadFileToBucket(invoiceFileName);
            File file = new File(invoiceFileName);
            file.delete();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
