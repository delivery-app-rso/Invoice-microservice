package si.fri.rso.invoicemicroservice.models.converters;

import si.fri.rso.invoicemicroservice.lib.InvoiceItem;
import si.fri.rso.invoicemicroservice.models.entities.InvoiceItemEntity;

public class InvoiceItemConverter {

    public static InvoiceItem toDto(InvoiceItemEntity entity) {

        InvoiceItem dto = new InvoiceItem();
        dto.setId(entity.getId());
        dto.setItemId(entity.getItemId());

        return dto;

    }

    public static InvoiceItemEntity toEntity(InvoiceItem dto) {

        InvoiceItemEntity entity = new InvoiceItemEntity();
        entity.setId(dto.getId());
        entity.setItemId(dto.getItemId());

        return entity;

    }
}
