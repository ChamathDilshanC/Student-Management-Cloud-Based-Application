package com.studentmanagementapp.studentmanagementapp.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.studentmanagementapp.studentmanagementapp.service.StorageService;

/**
 * Local file-system implementation of StorageService.
 * Active on the "local" profile.
 */
@Service
@Profile("local")
public class LocalStorageServiceImpl implements StorageService {

  @Value("${app.storage.upload-dir:uploads/}")
  private String uploadDir;

  @Override
  public String uploadFile(MultipartFile file, String folder) {
    if (file == null || file.isEmpty())
      return null;

    String originalFilename = file.getOriginalFilename();
    String extension = "";
    if (originalFilename != null && originalFilename.contains(".")) {
      extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
    }

    String uniqueFileName = UUID.randomUUID() + extension;

    // Resolve to an absolute path anchored at the project working directory
    // so files are never written into Tomcat's temp directory
    Path baseDir = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
    Path dirPath = baseDir.resolve(uploadDir).resolve(folder);

    try {
      Files.createDirectories(dirPath);
      Path filePath = dirPath.resolve(uniqueFileName);
      file.transferTo(filePath.toAbsolutePath().toFile());
      // Return a URL-friendly relative path with forward slashes
      return "/" + uploadDir + folder + "/" + uniqueFileName;
    } catch (IOException e) {
      throw new RuntimeException("Failed to store file locally: " + e.getMessage(), e);
    }
  }

  @Override
  public void deleteFile(String fileUrl) {
    if (fileUrl == null || fileUrl.isBlank())
      return;
    String relativePath = fileUrl.startsWith("/") ? fileUrl.substring(1) : fileUrl;
    Path baseDir = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
    File file = baseDir.resolve(relativePath).toFile();
    if (file.exists()) {
      file.delete();
    }
  }
}
