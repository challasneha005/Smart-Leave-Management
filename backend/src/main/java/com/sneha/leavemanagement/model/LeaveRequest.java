package com.sneha.leavemanagement.model;
import jakarta.persistence.*;
import java.time.*;
@Entity @Table(name="leave_requests", indexes={@Index(name="idx_leave_status",columnList="status"),@Index(name="idx_leave_employee",columnList="employee_id")})
public class LeaveRequest {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="employee_id") private Employee employee;
 @ManyToOne(optional=false) @JoinColumn(name="leave_type_id") private LeaveType leaveType;
 @Column(nullable=false) private LocalDate startDate;
 @Column(nullable=false) private LocalDate endDate;
 @Column(nullable=false,length=500) private String reason;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private LeaveStatus status=LeaveStatus.PENDING;
 @Column(nullable=false) private LocalDateTime createdAt=LocalDateTime.now();
 @Column(length=500) private String decisionComment;
 @Column private LocalDateTime decidedAt;
 public Long getId(){return id;} public Employee getEmployee(){return employee;} public void setEmployee(Employee v){employee=v;} public LeaveType getLeaveType(){return leaveType;} public void setLeaveType(LeaveType v){leaveType=v;}
 public LocalDate getStartDate(){return startDate;} public void setStartDate(LocalDate v){startDate=v;} public LocalDate getEndDate(){return endDate;} public void setEndDate(LocalDate v){endDate=v;} public String getReason(){return reason;} public void setReason(String v){reason=v;}
 public LeaveStatus getStatus(){return status;} public void setStatus(LeaveStatus v){status=v;} public LocalDateTime getCreatedAt(){return createdAt;} public String getDecisionComment(){return decisionComment;} public void setDecisionComment(String v){decisionComment=v;} public LocalDateTime getDecidedAt(){return decidedAt;} public void setDecidedAt(LocalDateTime v){decidedAt=v;}
}
