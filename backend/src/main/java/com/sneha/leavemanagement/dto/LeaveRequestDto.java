package com.sneha.leavemanagement.dto;
import jakarta.validation.constraints.*; import java.time.LocalDate;
public record LeaveRequestDto(@NotNull Long leaveTypeId,@NotNull @FutureOrPresent LocalDate startDate,@NotNull LocalDate endDate,@NotBlank @Size(min=5,max=500) String reason) {}
