package si.fri.rso.invoicemicroservice.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "invoice_items")
@NamedQueries(value = {
        @NamedQuery(name = "InvoiceItemEntity.getAll", query = "SELECT im FROM InvoiceItemEntity im")
})
public class InvoiceItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "invoice_id")
    private InvoiceEntity invoice;

    @Column(name = "itemId")
    private Integer itemId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InvoiceEntity getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceEntity invoice) {
        this.invoice = invoice;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
