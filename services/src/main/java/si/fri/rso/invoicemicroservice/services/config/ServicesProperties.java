package si.fri.rso.invoicemicroservice.services.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("microservices")
public class ServicesProperties {

    @ConfigValue(watch = true)
    private String itemsServiceHost;

    @ConfigValue(watch = true)
    private String minioHost;

    public String getItemsServiceHost() {
        return itemsServiceHost;
    }

    public void setItemsServiceHost(String itemsServiceHost) {
        this.itemsServiceHost = itemsServiceHost;
    }

    public String getMinioHost() {
        return minioHost;
    }

    public void setMinioHost(String minioHost) {
        this.minioHost = minioHost;
    }
}
