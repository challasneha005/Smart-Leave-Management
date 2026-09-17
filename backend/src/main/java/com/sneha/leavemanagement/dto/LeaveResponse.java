package com.sneha.leavemanagement.dto;
import com.sneha.leavemanagement.model.LeaveStatus; import java.time.*;
public record LeaveResponse(Long id,Long employeeId,String employeeName,String department,String leaveType,LocalDate startDate,LocalDate endDate,long days,String reason,LeaveStatus status,String decisionComment,LocalDateTime createdAt) {}
