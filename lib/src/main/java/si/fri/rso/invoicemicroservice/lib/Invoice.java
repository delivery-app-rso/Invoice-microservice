package si.fri.rso.invoicemicroservice.lib;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Invoice {
    private Integer id;

    private Integer userId;

    private Double amount;

    private Date createdAt;

    private List<InvoiceItem> invoiceItems = new ArrayList<>();

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public void addInvoiceItem(InvoiceItem invoiceItem) {
        if (this.invoiceItems == null) {
            this.invoiceItems = new ArrayList<>();
        }

        this.invoiceItems.add(invoiceItem);
    }

}
