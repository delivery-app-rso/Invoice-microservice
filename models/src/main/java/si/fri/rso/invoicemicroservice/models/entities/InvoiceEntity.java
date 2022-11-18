package si.fri.rso.invoicemicroservice.models.entities;

import java.util.Date;
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

    //TODO Change to user table relations mapping
    @Column(name="userId")
    private Integer userId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "otp")
    private String otp;

    @Column(name="payed")
    private Boolean payed;

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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Boolean getPayed() {
        return payed;
    }

    public void setPayed(Boolean payed) {
        this.payed = payed;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
