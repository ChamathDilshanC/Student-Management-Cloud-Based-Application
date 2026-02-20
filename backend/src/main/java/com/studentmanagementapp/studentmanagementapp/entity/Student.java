package com.studentmanagementapp.studentmanagementapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "f_name", nullable = false)
  private String fName;

  @Column(name = "l_name", nullable = false)
  private String lName;

  @Column(nullable = false, unique = true)
  private String contact;

  @Column(nullable = false)
  private String address;

  /**
   * Stores the public URL of the student's profile picture.
   * - Local mode : /uploads/students/uuid.jpg (served by Spring static handler)
   * - GCS mode : https://storage.googleapis.com/bucket/students/uuid.jpg
   */
  @Column(name = "picture", length = 2048)
  private String picture;
}
