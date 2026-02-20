package com.studentmanagementapp.studentmanagementapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.studentmanagementapp.studentmanagementapp.dto.StudentDTO;
import com.studentmanagementapp.studentmanagementapp.service.StudentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

  private final StudentService studentService;

  /**
   * GET /api/v1/students
   * Returns a list of all students.
   */
  @GetMapping
  public ResponseEntity<List<StudentDTO>> getAllStudents() {
    return ResponseEntity.ok(studentService.getAllStudents());
  }

  /**
   * GET /api/v1/students/{id}
   * Returns a single student by ID.
   */
  @GetMapping("/{id}")
  public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
    return ResponseEntity.ok(studentService.getStudentById(id));
  }

  /**
   * POST /api/v1/students
   * Saves a new student.
   * Send as multipart/form-data:
   * - "student" part: JSON StudentDTO fields (fName, lName, contact, address)
   * - "profileImage" part: image file (optional)
   */
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<StudentDTO> saveStudent(
      @RequestPart("fName") String fName,
      @RequestPart("lName") String lName,
      @RequestPart("contact") String contact,
      @RequestPart("address") String address,
      @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
    StudentDTO dto = new StudentDTO(null, fName, lName, contact, address, null);
    StudentDTO saved = studentService.saveStudent(dto, profileImage);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
  }

  /**
   * PUT /api/v1/students/{id}
   * Updates an existing student.
   * Same multipart format as POST.
   */
  @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<StudentDTO> updateStudent(
      @PathVariable Long id,
      @RequestPart("fName") String fName,
      @RequestPart("lName") String lName,
      @RequestPart("contact") String contact,
      @RequestPart("address") String address,
      @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
    StudentDTO dto = new StudentDTO(id, fName, lName, contact, address, null);
    StudentDTO updated = studentService.updateStudent(id, dto, profileImage);
    return ResponseEntity.ok(updated);
  }

  /**
   * DELETE /api/v1/students/{id}
   * Deletes a student and their profile picture.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
    studentService.deleteStudent(id);
    return ResponseEntity.noContent().build();
  }
}
