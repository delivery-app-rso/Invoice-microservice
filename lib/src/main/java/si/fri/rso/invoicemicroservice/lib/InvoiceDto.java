package si.fri.rso.invoicemicroservice.lib;

public class InvoiceDto {
    // TODO: Add additional required data
    private Integer userId;

    private Integer itemId;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
