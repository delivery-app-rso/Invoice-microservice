package si.fri.rso.invoicemicroservice.services.files;

import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import si.fri.rso.invoicemicroservice.services.config.MinioProperties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class MinioHandler {

    private MinioClient minioClient;

    @Inject
    private MinioProperties minioProperties;

    @PostConstruct
    public void initMinioClient() {
        this.minioClient = MinioClient.builder()
                .endpoint(this.minioProperties.getHost(), this.minioProperties.getPort(), false)
                .credentials(this.minioProperties.getAccessKey(), this.minioProperties.getSecret())
                .build();
    }

    public void uploadFileToBucket(String fileName) {
        try {
            minioClient.uploadObject(
                    UploadObjectArgs.builder().bucket(this.minioProperties.getInvoicesBucket()).object(fileName)
                            .filename(fileName).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
