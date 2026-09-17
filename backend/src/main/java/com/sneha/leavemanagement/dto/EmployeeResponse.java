package com.sneha.leavemanagement.dto;
import java.time.LocalDate;
public record EmployeeResponse(Long id,String name,String email,String department,int leaveBalance,
 LocalDate joiningDate,boolean active,String username,String role,Long managerId,String managerName) {}
