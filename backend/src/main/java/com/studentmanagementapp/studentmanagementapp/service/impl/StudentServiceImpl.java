package com.studentmanagementapp.studentmanagementapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.studentmanagementapp.studentmanagementapp.dto.StudentDTO;
import com.studentmanagementapp.studentmanagementapp.entity.Student;
import com.studentmanagementapp.studentmanagementapp.exception.ResourceNotFoundException;
import com.studentmanagementapp.studentmanagementapp.repository.StudentRepository;
import com.studentmanagementapp.studentmanagementapp.service.StorageService;
import com.studentmanagementapp.studentmanagementapp.service.StudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

  private final StudentRepository studentRepository;
  private final StorageService storageService;

  @Override
  public List<StudentDTO> getAllStudents() {
    return studentRepository.findAll()
        .stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  @Override
  public StudentDTO getStudentById(Long id) {
    return toDTO(findOrThrow(id));
  }

  @Override
  public StudentDTO saveStudent(StudentDTO dto, MultipartFile profileImage) {
    if (studentRepository.existsByContact(dto.getContact())) {
      throw new IllegalArgumentException(
          "A student with contact '" + dto.getContact() + "' already exists.");
    }

    Student student = toEntity(dto);

    if (profileImage != null && !profileImage.isEmpty()) {
      student.setPicture(storageService.uploadFile(profileImage, "students"));
    }

    return toDTO(studentRepository.save(student));
  }

  @Override
  public StudentDTO updateStudent(Long id, StudentDTO dto, MultipartFile profileImage) {
    Student existing = findOrThrow(id);

    if (!existing.getContact().equals(dto.getContact())
        && studentRepository.existsByContact(dto.getContact())) {
      throw new IllegalArgumentException(
          "A student with contact '" + dto.getContact() + "' already exists.");
    }

    existing.setFName(dto.getFName());
    existing.setLName(dto.getLName());
    existing.setContact(dto.getContact());
    existing.setAddress(dto.getAddress());

    if (profileImage != null && !profileImage.isEmpty()) {
      if (existing.getPicture() != null) {
        storageService.deleteFile(existing.getPicture());
      }
      existing.setPicture(storageService.uploadFile(profileImage, "students"));
    }

    return toDTO(studentRepository.save(existing));
  }

  @Override
  public void deleteStudent(Long id) {
    Student student = findOrThrow(id);
    if (student.getPicture() != null) {
      storageService.deleteFile(student.getPicture());
    }
    studentRepository.delete(student);
  }

  // ── Helpers ──────────────────────────────────────────────────────────────

  private Student findOrThrow(Long id) {
    return studentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
  }

  private StudentDTO toDTO(Student s) {
    return new StudentDTO(s.getId(), s.getFName(), s.getLName(),
        s.getContact(), s.getAddress(), s.getPicture());
  }

  private Student toEntity(StudentDTO dto) {
    Student s = new Student();
    s.setFName(dto.getFName());
    s.setLName(dto.getLName());
    s.setContact(dto.getContact());
    s.setAddress(dto.getAddress());
    return s;
  }
}
