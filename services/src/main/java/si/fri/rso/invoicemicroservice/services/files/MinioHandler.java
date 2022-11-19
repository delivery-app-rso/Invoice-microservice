package si.fri.rso.invoicemicroservice.services.files;

import io.github.cdimascio.dotenv.Dotenv;
import io.minio.DownloadObjectArgs;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import si.fri.rso.invoicemicroservice.services.config.MinioProperties;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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
                    UploadObjectArgs.builder().bucket("invoices").object(fileName).filename(fileName).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
