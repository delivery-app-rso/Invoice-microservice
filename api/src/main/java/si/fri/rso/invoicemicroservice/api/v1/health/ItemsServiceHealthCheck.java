package si.fri.rso.invoicemicroservice.api.v1.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import si.fri.rso.invoicemicroservice.services.config.ServicesProperties;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Readiness
@ApplicationScoped
public class ItemsServiceHealthCheck implements HealthCheck {

    @Inject
    private ServicesProperties servicesProperties;

    @Override
    public HealthCheckResponse call() {
        try {

            HttpURLConnection connection = (HttpURLConnection) new URL(
                    servicesProperties.getItemsServiceHost() + "/v1/items/status")
                    .openConnection();
            connection.setRequestMethod("HEAD");

            if (connection.getResponseCode() == 200) {
                return HealthCheckResponse.named(ItemsServiceHealthCheck.class.getSimpleName()).up().build();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return HealthCheckResponse.named(ItemsServiceHealthCheck.class.getSimpleName()).down().build();
    }
}
