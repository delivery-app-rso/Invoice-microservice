package si.fri.rso.invoicemicroservice.services.beans;

import si.fri.rso.invoicemicroservice.lib.Invoice;
import si.fri.rso.invoicemicroservice.lib.InvoiceDto;
import si.fri.rso.invoicemicroservice.models.converters.InvoiceConverter;
import si.fri.rso.invoicemicroservice.models.entities.InvoiceEntity;
import si.fri.rso.invoicemicroservice.models.entities.InvoiceItemEntity;
import si.fri.rso.invoicemicroservice.services.pdfs.PdfGenerator;
import si.fri.rso.invoicemicroservice.services.templates.TemplateEngine;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;

import org.eclipse.microprofile.metrics.annotation.Timed;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestScoped
public class InvoiceBean {
    private Logger log = Logger.getLogger(InvoiceBean.class.getName());

    @Inject
    private EntityManager em;

    @Inject
    PdfGenerator pdfGenerator;

    @Inject
    ServicesBean servicesBean;

    public List<Invoice> getInvoices() {
        TypedQuery<InvoiceEntity> query = em.createNamedQuery(
                "InvoiceEntity.getAll", InvoiceEntity.class);

        List<InvoiceEntity> resultList = query.getResultList();
        return resultList.stream().map(InvoiceConverter::toDto).collect(Collectors.toList());
    }

    public Invoice getInvoice(Integer id) {
        InvoiceEntity invoiceEntity = em.find(InvoiceEntity.class, id);

        if (invoiceEntity == null) {
            throw new NotFoundException();
        }

        Invoice invoice = InvoiceConverter.toDto(invoiceEntity);

        return invoice;
    }

    @Timed
    public Invoice createInvoice(InvoiceDto invoiceDto) {
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setUserId(invoiceDto.getUserId());
        invoiceEntity.setFilename("invoice-" + UUID.randomUUID());
        invoiceEntity.setAmount(90.0); // TODO: remove magic numbers and get them by calculating data from other
                                       // microservices

        this.persistEntity(invoiceEntity);

        if (invoiceEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        InvoiceItemEntity invoiceItemEntity = this.createInvoiceItemEntry(invoiceEntity, invoiceDto.getItemId());
        invoiceEntity.addInvoiceItem(invoiceItemEntity);
        this.pdfGenerator.generate(InvoiceConverter.toDto(invoiceEntity), this.servicesBean);
        this.servicesBean.sendInvoiceEmail(InvoiceConverter.toDto(invoiceEntity));

        return InvoiceConverter.toDto(invoiceEntity);
    }

    private InvoiceItemEntity createInvoiceItemEntry(InvoiceEntity invoiceEntity, Integer itemId) {
        InvoiceItemEntity invoiceItemEntity = new InvoiceItemEntity();
        invoiceItemEntity.setInvoice(invoiceEntity);
        invoiceItemEntity.setItemId(itemId);

        this.persistEntity(invoiceItemEntity);

        if (invoiceItemEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return invoiceItemEntity;
    }

    private <T> void persistEntity(T entity) {
        try {
            beginTx();
            em.persist(entity);
            commitTx();
            log.log(Level.INFO, "Persisted " + entity.toString());
        } catch (Exception e) {
            rollbackTx();
            log.log(Level.INFO, "Failed to persist entity. Rolling back.");
        }
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}