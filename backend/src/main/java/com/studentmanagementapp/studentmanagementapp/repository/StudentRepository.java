package com.studentmanagementapp.studentmanagementapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentmanagementapp.studentmanagementapp.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
  boolean existsByContact(String contact);
}
