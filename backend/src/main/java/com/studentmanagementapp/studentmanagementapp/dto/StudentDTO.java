package com.studentmanagementapp.studentmanagementapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

  private Long id;

  @NotBlank(message = "First name is required")
  private String fName;

  @NotBlank(message = "Last name is required")
  private String lName;

  @NotBlank(message = "Contact number is required")
  private String contact;

  @NotBlank(message = "Address is required")
  private String address;

  private String picture;
}
