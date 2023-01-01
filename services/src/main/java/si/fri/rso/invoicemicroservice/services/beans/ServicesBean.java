package si.fri.rso.invoicemicroservice.services.beans;

import java.util.HashMap;
import java.util.concurrent.CompletionStage;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.json.JSONObject;

import si.fri.rso.invoicemicroservice.lib.Invoice;
import si.fri.rso.invoicemicroservice.lib.InvoiceMailDto;
import si.fri.rso.invoicemicroservice.services.clients.InvoiceMailProcessApi;
import si.fri.rso.invoicemicroservice.services.config.ServicesProperties;

@ApplicationScoped
public class ServicesBean {

    @Inject
    ServicesProperties servicesProperties;

    @Inject
    @RestClient
    private InvoiceMailProcessApi invoiceMailProcessApi;

    private Client httpClient;

    @PostConstruct
    public void init() {
        this.httpClient = ClientBuilder.newClient();
    }

    public JSONObject getItem(Integer itemId) {
        try {
            String itemStringObject = this.httpClient
                    .target(servicesProperties.getItemsServiceHost() + "/v1/items/" + itemId)
                    .request().get(new GenericType<String>() {
                    });

            JSONObject itemJsonObject = new JSONObject(itemStringObject);

            return itemJsonObject;
        } catch (WebApplicationException | ProcessingException e) {
            System.out.println(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    public JSONObject getItemFallback(Integer itemId) {
        return null;
    }

    public void sendInvoiceEmail(Invoice invoice) {
        HashMap<String, String> invoiceData = new HashMap<>();

        invoiceData.put("filename", invoice.getFilename());
        invoiceData.put("amount", String.valueOf(invoice.getAmount()));
        invoiceData.put("itemId", String.valueOf(invoice.getInvoiceItems().get(0).getItemId()));

        CompletionStage<String> stringCompletionStage = invoiceMailProcessApi
                .sendMailAsynch(new InvoiceMailDto("invoice", invoice.getUserId(), invoiceData));

        stringCompletionStage.whenComplete((s, throwable) -> System.out.println("Finished sending mail: " + s));
    }
}
