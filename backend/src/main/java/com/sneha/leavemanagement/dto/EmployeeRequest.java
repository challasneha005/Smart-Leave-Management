package com.sneha.leavemanagement.dto;
import jakarta.validation.constraints.*; import java.time.LocalDate;
public record EmployeeRequest(@NotBlank @Size(max=100) String name,@NotBlank @Email String email,
 @NotNull Long departmentId,@NotNull @PastOrPresent LocalDate joiningDate,
 @NotBlank String username,@NotBlank @Size(min=8,max=100) String password,String role,Long managerId) {}
