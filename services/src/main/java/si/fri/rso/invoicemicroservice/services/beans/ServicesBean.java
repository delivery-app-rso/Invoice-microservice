package si.fri.rso.invoicemicroservice.services.beans;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

import si.fri.rso.invoicemicroservice.lib.Invoice;
import si.fri.rso.invoicemicroservice.services.config.ServicesProperties;

@ApplicationScoped
public class ServicesBean {

    @Inject
    ServicesProperties servicesProperties;

    private Client httpClient;
    private HttpClient client;

    @PostConstruct
    public void init() {
        this.httpClient = ClientBuilder.newClient();
        this.client = HttpClient.newHttpClient();
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

    public void sendInvoiceEmail(Invoice invoice) {
        try {
            System.out.println(invoice.getFilename());
            System.out.println(invoice.getInvoiceItems().get(0).getItemId());

            JSONObject invoiceData = new JSONObject();
            invoiceData.put("filename", invoice.getFilename());
            invoiceData.put("amount", invoice.getAmount());
            invoiceData.put("itemId", invoice.getInvoiceItems().get(0).getItemId());

            JSONObject body = new JSONObject();
            body.put("type", "invoice");
            body.put("userId", invoice.getUserId());

            body.put("invoiceData", invoiceData);
            System.out.println(body);
            System.out.println(body.toString());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(this.servicesProperties.getMailingServiceHost() + "/v1/mailing"))
                    .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.statusCode());

            System.out.println(response.body());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }
}
