package si.fri.rso.invoicemicroservice.lib;

import java.util.Map;

public class InvoiceDto {
    // TODO: Add additional required data
    private Map<String, String> userData;

    private Map<String, String> deliveryData;

    public Map<String, String> getUserData() {
        return userData;
    }

    public void setUserData(Map<String, String> userData) {
        this.userData = userData;
    }

    public Map<String, String> getDeliveryData() {
        return deliveryData;
    }

    public void setDeliveryData(Map<String, String> deliveryData) {
        this.deliveryData = deliveryData;
    }

}
