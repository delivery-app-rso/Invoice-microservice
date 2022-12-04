package si.fri.rso.invoicemicroservice.services.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("templates")
public class TemplateEngineProperties {

    @ConfigValue(watch = true)
    private String environment;

    public String getTemplatesPath() {
        return environment.equals("dev") ? "services/src/main/resources/templates/" : "/app/templates/";
    }

    public String getEnviroment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }
}
