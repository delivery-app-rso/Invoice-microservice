package si.fri.rso.invoicemicroservice.models.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "invoices")
@NamedQueries(value = {
        @NamedQuery(name = "InvoiceEntity.getAll", query = "SELECT im FROM InvoiceEntity im")
})
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.PERSIST)
    private List<InvoiceItemEntity> invoiceItems = new ArrayList<>();

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "filename")
    private String filename;

    @Column(name = "createdAt")
    private Date createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<InvoiceItemEntity> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItemEntity> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public void addInvoiceItem(InvoiceItemEntity invoiceItem) {
        if (this.invoiceItems == null) {
            this.invoiceItems = new ArrayList<>();
        }

        this.invoiceItems.add(invoiceItem);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
