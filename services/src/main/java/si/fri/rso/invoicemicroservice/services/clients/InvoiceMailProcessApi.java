package si.fri.rso.invoicemicroservice.services.clients;

import java.util.concurrent.CompletionStage;

import javax.enterprise.context.Dependent;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import si.fri.rso.invoicemicroservice.lib.InvoiceMailDto;

@Path("/mailing")
@RegisterRestClient(configKey = "invoice-mail-process-api")
@Dependent
public interface InvoiceMailProcessApi {
    @POST
    CompletionStage<String> sendMailAsynch(InvoiceMailDto mailingDto);
}