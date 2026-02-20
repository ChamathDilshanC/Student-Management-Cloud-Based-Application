package com.studentmanagementapp.studentmanagementapp.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
  /**
   * Upload a file and return its accessible URL.
   *
   * @param file   the multipart file to store
   * @param folder sub-folder / path prefix (e.g. "students")
   * @return public URL or relative URL of the stored file
   */
  String uploadFile(MultipartFile file, String folder);

  /**
   * Delete a previously uploaded file.
   *
   * @param fileUrlOrName the URL or file-name returned by uploadFile
   */
  void deleteFile(String fileUrlOrName);
}
