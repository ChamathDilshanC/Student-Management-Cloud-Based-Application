package com.studentmanagementapp.studentmanagementapp.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.studentmanagementapp.studentmanagementapp.dto.StudentDTO;

public interface StudentService {
  List<StudentDTO> getAllStudents();

  StudentDTO getStudentById(Long id);

  StudentDTO saveStudent(StudentDTO dto, MultipartFile profileImage);

  StudentDTO updateStudent(Long id, StudentDTO dto, MultipartFile profileImage);

  void deleteStudent(Long id);
}
