package com.example.demo.crud.service;

import java.io.FileInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Component
public class GCSAccess {

	private static final Logger logger = LoggerFactory.getLogger(GCSAccess.class);
//	private Storage storage = StorageOptions.getDefaultInstance().getService();
	private Storage storage;
	
	@Value("${gcp.bucket.name}")
    private String bucketName;

    @Value("${gcp.credentials.path}")
    private String credentialsPath;
	
	
	private void initStorage() throws Exception {
        if (storage == null) {
            storage = StorageOptions.newBuilder()
                    .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(credentialsPath)))
                    .build()
                    .getService();
            logger.info("Initialized GCS client with key: {}", credentialsPath);
        }
    }

	public String GCSSaveFile(String type, MultipartFile multipartFile) {
		try {
			initStorage();
			if (multipartFile == null) {
				logger.warn("No file uploaded for type: {}", type);
				return null;
			}
			String subDirName = type.equalsIgnoreCase("image") ? "images" : "files";
			String originalFilename = multipartFile.getOriginalFilename();

			if (originalFilename == null || originalFilename.trim().isEmpty()) {
				logger.warn("Uploaded file has no name.");
				return null;
			}
			String uniqueFilename = subDirName + "/" + System.currentTimeMillis() + "_" + originalFilename;

			System.out.println("Bucket: " + bucketName); // or use logger
			System.out.println("Blob Name: " + originalFilename);
			
			// Upload to GCS
			BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uniqueFilename).build();
			storage.create(blobInfo, multipartFile.getBytes());

			String fileUrl = String.format("https://storage.googleapis.com/%s/%s", bucketName, uniqueFilename);
			logger.info("Uploaded {} to {}", type, fileUrl);

			return fileUrl;
		} catch (Exception e) {
			logger.error("Error uploading {} to GCS: {}", type, e.getMessage(), e);
			return null;
		}
	}
}
