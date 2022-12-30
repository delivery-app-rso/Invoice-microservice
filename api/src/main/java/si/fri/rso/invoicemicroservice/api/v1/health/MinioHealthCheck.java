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
public class MinioHealthCheck implements HealthCheck {

    @Inject
    private ServicesProperties servicesProperties;

    @Override
    public HealthCheckResponse call() {
        try {

            HttpURLConnection connection = (HttpURLConnection) new URL(servicesProperties.getMinioHost())
                    .openConnection();
            connection.setRequestMethod("HEAD");

            if (connection.getResponseCode() == 200) {
                return HealthCheckResponse.named(MinioHealthCheck.class.getSimpleName()).up().build();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return HealthCheckResponse.named(MinioHealthCheck.class.getSimpleName()).down().build();
    }
}
