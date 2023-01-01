package si.fri.rso.invoicemicroservice.lib;

import java.util.HashMap;
import java.util.Map;

public class InvoiceMailDto {
    private String type;

    private Integer userId;

    private Map<String, String> invoiceData;

    public InvoiceMailDto(String type, Integer userId, HashMap<String, String> invoiceData) {
        this.type = type;
        this.userId = userId;
        this.invoiceData = invoiceData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Map<String, String> getInvoiceData() {
        return invoiceData;
    }

    public void setInvoiceData(HashMap<String, String> invoiceData) {
        this.invoiceData = invoiceData;
    }
}
