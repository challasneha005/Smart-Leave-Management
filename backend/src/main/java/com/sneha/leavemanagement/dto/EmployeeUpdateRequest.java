package com.sneha.leavemanagement.dto;
import jakarta.validation.constraints.*;
public record EmployeeUpdateRequest(@NotBlank @Size(max=100) String name,@NotBlank @Email String email,@NotNull Long departmentId,boolean active) {}
