package com.studentmanagementapp.studentmanagementapp.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.studentmanagementapp.studentmanagementapp.service.StorageService;

/**
 * Google Cloud Storage implementation of StorageService.
 * Active on the "cloud" profile.
 */
@Service
@Profile("cloud")
public class GcsStorageServiceImpl implements StorageService {

  @Value("${app.storage.gcs.bucket-name}")
  private String bucketName;

  @Value("${app.storage.gcs.credentials-path}")
  private String credentialsPath;

  private final ResourceLoader resourceLoader;

  public GcsStorageServiceImpl(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  private Storage buildStorageClient() throws IOException {
    try (InputStream credentialsStream = resourceLoader.getResource(credentialsPath).getInputStream()) {
      GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
          .createScoped("https://www.googleapis.com/auth/cloud-platform");
      return StorageOptions.newBuilder()
          .setCredentials(credentials)
          .build()
          .getService();
    }
  }

  @Override
  public String uploadFile(MultipartFile file, String folder) {
    if (file == null || file.isEmpty())
      return null;

    String originalFilename = file.getOriginalFilename();
    String extension = "";
    if (originalFilename != null && originalFilename.contains(".")) {
      extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
    }

    String objectName = folder + "/" + UUID.randomUUID() + extension;

    try {
      Storage storage = buildStorageClient();
      BlobId blobId = BlobId.of(bucketName, objectName);
      BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
          .setContentType(file.getContentType())
          .build();
      storage.createFrom(blobInfo, file.getInputStream());

      // Return a Signed URL (valid for 7 days)
      // This works even if the bucket is private (Uniform Bucket-Level Access)
      return storage.signUrl(blobInfo, 7, java.util.concurrent.TimeUnit.DAYS,
          com.google.cloud.storage.Storage.SignUrlOption.withV4Signature()).toString();
    } catch (IOException e) {
      throw new RuntimeException("Failed to upload file to GCS: " + e.getMessage(), e);
    }
  }

  @Override
  public void deleteFile(String fileUrl) {
    if (fileUrl == null || fileUrl.isBlank())
      return;

    // Extract object name from the full GCS URL (handles signed URLs with query
    // params)
    String prefix = "https://storage.googleapis.com/" + bucketName + "/";
    if (!fileUrl.startsWith(prefix))
      return;

    String objectName = fileUrl.substring(prefix.length());

    // Remove query parameters from signed URLs (e.g., ?X-Goog-Algorithm=...)
    int queryIndex = objectName.indexOf('?');
    if (queryIndex != -1) {
      objectName = objectName.substring(0, queryIndex);
    }

    try {
      Storage storage = buildStorageClient();
      storage.delete(BlobId.of(bucketName, objectName));
    } catch (IOException e) {
      throw new RuntimeException("Failed to delete file from GCS: " + e.getMessage(), e);
    }
  }
}
