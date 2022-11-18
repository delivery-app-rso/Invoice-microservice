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

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class MinioHandler {

    private MinioClient minioClient;

    public MinioHandler() {
        Dotenv dotenv = Dotenv.load();

        // TODO: Read from config server
        this.minioClient = MinioClient.builder()
                .endpoint("http://localhost:9000")
                .credentials(dotenv.get("MINIO_ACCESS_KEY"), dotenv.get("MINIO_SECRET_KEY"))
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
