package si.fri.rso.invoicemicroservice.models.converters;


import si.fri.rso.invoicemicroservice.lib.Invoice;
import si.fri.rso.invoicemicroservice.models.entities.InvoiceEntity;

public class InvoiceConverter {

    public static Invoice toDto(InvoiceEntity entity) {

        Invoice dto = new Invoice();
        dto.setId(entity.getId());
        dto.setAmount(entity.getAmount());
        dto.setUserId(entity.getUserId());
        dto.setOtp(entity.getOtp());
        dto.setPayed(entity.getPayed());

        return dto;

    }

    public static InvoiceEntity toEntity(Invoice dto) {
        InvoiceEntity entity = new InvoiceEntity();
        entity.setId(dto.getId());
        entity.setAmount(dto.getAmount());
        entity.setUserId(dto.getUserId());
        entity.setOtp(dto.getOtp());
        entity.setPayed(dto.getPayed());

        return entity;

    }
}
