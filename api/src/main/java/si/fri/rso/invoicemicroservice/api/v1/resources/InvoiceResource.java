package si.fri.rso.invoicemicroservice.api.v1.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.rso.invoicemicroservice.lib.Invoice;
import si.fri.rso.invoicemicroservice.lib.InvoiceDto;
import si.fri.rso.invoicemicroservice.services.beans.InvoiceBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Path("/invoices")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvoiceResource {

        @Inject
        private InvoiceBean invoiceBean;

        private Logger log = Logger.getLogger(InvoiceResource.class.getName());

        @Operation(description = "Get all invoices.", summary = "Get all invoices")
        @APIResponses({
                        @APIResponse(responseCode = "200", description = "List of invoices", content = @Content(schema = @Schema(implementation = Invoice.class, type = SchemaType.ARRAY)), headers = {
                                        @Header(name = "X-Total-Count", description = "Number of objects in list") }) })
        @GET
        public Response getMails() {
                List<Invoice> invoices = this.invoiceBean.getInvoices();
                return Response.status(Response.Status.OK).entity(invoices).build();
        }

        @Operation(description = "Get status", summary = "Get status")
        @APIResponses({ @APIResponse(responseCode = "200", description = "status") })
        @GET
        @Path("/status")
        public Response getStatus() {
                return Response.status(Response.Status.OK).build();
        }

        @Operation(description = "Get invoice.", summary = "Get invoice")
        @APIResponses({
                        @APIResponse(responseCode = "200", description = "invoice data", content = @Content(schema = @Schema(implementation = Invoice.class))) })
        @GET
        @Path("/{invoiceId}")
        public Response getInvoice(
                        @Parameter(description = "invoice ID.", required = true) @PathParam("invoiceId") Integer invoiceId) {

                Invoice invoice = invoiceBean.getInvoice(invoiceId);

                if (invoice == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                }

                return Response.status(Response.Status.OK).entity(invoice).build();
        }

        @Operation(description = "Store invoice in DB.", summary = "Store invoice")
        @APIResponses({
                        @APIResponse(responseCode = "201", description = "Invoice successfully added."),
                        @APIResponse(responseCode = "405", description = "Validation error .")
        })
        @POST
        public Response createInvoice(
                        @RequestBody(description = "DTO object with Invoice data.", required = true, content = @Content(schema = @Schema(implementation = InvoiceDto.class))) InvoiceDto invoiceDto) {
                if (invoiceDto.getUserId() == null || invoiceDto.getItemId() == null) {
                        return Response.status(Response.Status.BAD_REQUEST).build();
                }

                Invoice invoice = this.invoiceBean.createInvoice(invoiceDto);
                return Response.status(Response.Status.OK).entity(invoice).build();
        }
}
