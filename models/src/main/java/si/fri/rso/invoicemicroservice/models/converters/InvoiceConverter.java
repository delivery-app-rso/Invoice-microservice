package si.fri.rso.invoicemicroservice.models.converters;

import java.util.ArrayList;
import java.util.List;

import si.fri.rso.invoicemicroservice.lib.Invoice;
import si.fri.rso.invoicemicroservice.lib.InvoiceItem;
import si.fri.rso.invoicemicroservice.models.entities.InvoiceEntity;
import si.fri.rso.invoicemicroservice.models.entities.InvoiceItemEntity;

public class InvoiceConverter {

    public static Invoice toDto(InvoiceEntity entity) {

        Invoice dto = new Invoice();
        List<InvoiceItem> invoiceItems = new ArrayList<>();

        for (InvoiceItemEntity ent : entity.getInvoiceItems()) {
            invoiceItems.add(InvoiceItemConverter.toDto(ent));
        }

        dto.setId(entity.getId());
        dto.setInvoiceItems(invoiceItems);
        dto.setAmount(entity.getAmount());
        dto.setUserId(entity.getUserId());
        dto.setFilename(entity.getFilename());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;

    }

    public static InvoiceEntity toEntity(Invoice dto) {
        InvoiceEntity entity = new InvoiceEntity();
        List<InvoiceItemEntity> invoiceItemEntities = new ArrayList<>();

        for (InvoiceItem attachment : dto.getInvoiceItems()) {
            invoiceItemEntities.add(InvoiceItemConverter.toEntity(attachment));
        }

        entity.setId(dto.getId());
        entity.setInvoiceItems(invoiceItemEntities);
        entity.setAmount(dto.getAmount());
        entity.setFilename(dto.getFilename());
        entity.setUserId(dto.getUserId());
        entity.setCreatedAt(dto.getCreatedAt());

        return entity;

    }
}
