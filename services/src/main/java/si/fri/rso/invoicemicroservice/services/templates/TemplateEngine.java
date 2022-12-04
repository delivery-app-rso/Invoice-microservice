package si.fri.rso.invoicemicroservice.services.templates;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import si.fri.rso.invoicemicroservice.services.config.TemplateEngineProperties;

@RequestScoped
public class TemplateEngine {
    private Configuration cfg;

    @Inject
    private TemplateEngineProperties templateEngineProperties;

    public TemplateEngine() {}

    @PostConstruct
    private void configureEngine() {
        this.cfg = new Configuration(Configuration.VERSION_2_3_29);

        try {
            this.cfg.setDirectoryForTemplateLoading(
                    new File(this.templateEngineProperties.getTemplatesPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.cfg.setDefaultEncoding("UTF-8");
        this.cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        this.cfg.setLogTemplateExceptions(true);
        this.cfg.setWrapUncheckedExceptions(true);
        this.cfg.setFallbackOnNullLoopVariable(false);
    }

    private String compressHTMLString(String htmlString) {
        htmlString.replace("/\s+/", " ");
        htmlString.replace("/>\s+</", "><");

        return htmlString;
    }

    public String getTemplateHTML(String templateName, Map<String, String> dataModel) {
        try {
            Template template = this.cfg.getTemplate(templateName);

            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            Writer out = new OutputStreamWriter(outStream);
            template.process(dataModel, out);
            out.close();

            return this.compressHTMLString(outStream.toString());
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        return "";
    }
}
