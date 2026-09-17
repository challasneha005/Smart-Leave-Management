package com.sneha.leavemanagement.service;
import com.sneha.leavemanagement.dto.*; import com.sneha.leavemanagement.exception.*; import com.sneha.leavemanagement.model.*; import com.sneha.leavemanagement.repository.*;
import org.springframework.data.domain.*; import org.springframework.security.core.Authentication; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.time.*; import java.util.List;
@Service @Transactional
public class LeaveService{
 private final LeaveRequestRepository leaves; private final EmployeeRepository employees; private final LeaveTypeRepository types; private final AuditService audit;
 public LeaveService(LeaveRequestRepository l,EmployeeRepository e,LeaveTypeRepository t,AuditService a){leaves=l;employees=e;types=t;audit=a;}
 public LeaveResponse apply(Long employeeId,LeaveRequestDto d,Authentication a){
  Employee e=employees.findById(employeeId).orElseThrow(()->new ResourceNotFoundException("Employee not found"));
  if(!e.getUser().getUsername().equals(a.getName())) throw new BusinessException("You can only apply leave for your own account");
  if(!e.isActive())throw new BusinessException("Employee is inactive"); validateDates(d.startDate(),d.endDate()); long days=days(d.startDate(),d.endDate());
  if(days>e.getLeaveBalance())throw new BusinessException("Insufficient leave balance");
  if(leaves.hasOverlap(employeeId,d.startDate(),d.endDate(),List.of(LeaveStatus.PENDING,LeaveStatus.APPROVED)))throw new BusinessException("Leave dates overlap an existing pending or approved leave");
  LeaveType t=types.findById(d.leaveTypeId()).orElseThrow(()->new ResourceNotFoundException("Leave type not found"));
  LeaveRequest l=new LeaveRequest();l.setEmployee(e);l.setLeaveType(t);l.setStartDate(d.startDate());l.setEndDate(d.endDate());l.setReason(d.reason());leaves.save(l);
  audit.log(a,"APPLY","LEAVE_REQUEST",l.getId(),"Applied for "+days+" day(s) of "+t.getName());return toResponse(l);
 }
 public PageResponse<LeaveResponse> list(LeaveStatus status,int page,int size,Authentication a){
  Pageable p=PageRequest.of(Math.max(0,page),Math.min(Math.max(1,size),50),Sort.by("createdAt").descending());
  Page<LeaveRequest> r;
  Employee actor=employees.findByUserUsername(a.getName()).orElseThrow(()->new ResourceNotFoundException("Employee profile not found"));
  if(actor.getUser().getRole()==Role.ADMIN) r=status==null?leaves.findAllByOrderByCreatedAtDesc(p):leaves.findByStatusOrderByCreatedAtDesc(status,p);
  else if(actor.getUser().getRole()==Role.MANAGER) r=status==null?leaves.findByEmployeeManagerIdOrderByCreatedAtDesc(actor.getId(),p):leaves.findByEmployeeManagerIdAndStatusOrderByCreatedAtDesc(actor.getId(),status,p);
  else throw new BusinessException("Only managers and admins can view team leaves");
  return new PageResponse<>(r.map(this::toResponse).getContent(),r.getNumber(),r.getSize(),r.getTotalElements(),r.getTotalPages());
 }
 public List<LeaveResponse> mine(Long id){return leaves.findByEmployeeIdOrderByCreatedAtDesc(id).stream().map(this::toResponse).toList();}
 public LeaveResponse decide(Long id,boolean approve,String comment,Authentication a){
  LeaveRequest l=get(id); Employee actor=employees.findByUserUsername(a.getName()).orElseThrow(()->new ResourceNotFoundException("Approver profile not found"));
  if(actor.getUser().getRole()!=Role.ADMIN && (actor.getUser().getRole()!=Role.MANAGER || l.getEmployee().getManager()==null || !l.getEmployee().getManager().getId().equals(actor.getId()))) throw new BusinessException("You can decide only leave requests from your direct reports");
  if(l.getStatus()!=LeaveStatus.PENDING)throw new BusinessException("Only pending requests can be decided");
  if(approve){long days=days(l.getStartDate(),l.getEndDate());Employee e=l.getEmployee();if(e.getLeaveBalance()<days)throw new BusinessException("Insufficient leave balance");e.setLeaveBalance(e.getLeaveBalance()-(int)days);l.setStatus(LeaveStatus.APPROVED);audit.log(a,"APPROVE","LEAVE_REQUEST",id,"Approved leave request");}
  else{l.setStatus(LeaveStatus.REJECTED);audit.log(a,"REJECT","LEAVE_REQUEST",id,"Rejected leave request");}
  l.setDecisionComment(comment);l.setDecidedAt(LocalDateTime.now());return toResponse(l);
 }
 public LeaveResponse cancel(Long id,Authentication a){LeaveRequest l=get(id);if(!l.getEmployee().getUser().getUsername().equals(a.getName()))throw new BusinessException("You can only cancel your own leave");if(l.getStatus()!=LeaveStatus.PENDING&&l.getStatus()!=LeaveStatus.APPROVED)throw new BusinessException("Only pending or approved leave can be cancelled");if(l.getStatus()==LeaveStatus.APPROVED){Employee e=l.getEmployee();e.setLeaveBalance(e.getLeaveBalance()+(int)days(l.getStartDate(),l.getEndDate()));}l.setStatus(LeaveStatus.CANCELLED);audit.log(a,"CANCEL","LEAVE_REQUEST",id,"Cancelled leave request");return toResponse(l);}
 private LeaveRequest get(Long id){return leaves.findById(id).orElseThrow(()->new ResourceNotFoundException("Leave request not found"));}
 private void validateDates(LocalDate s,LocalDate e){if(e.isBefore(s))throw new BusinessException("End date cannot be before start date");if(s.isBefore(LocalDate.now()))throw new BusinessException("Leave cannot start in the past");}
 private long days(LocalDate s,LocalDate e){return e.toEpochDay()-s.toEpochDay()+1;}
 private LeaveResponse toResponse(LeaveRequest l){return new LeaveResponse(l.getId(),l.getEmployee().getId(),l.getEmployee().getName(),l.getEmployee().getDepartment().getName(),l.getLeaveType().getName(),l.getStartDate(),l.getEndDate(),days(l.getStartDate(),l.getEndDate()),l.getReason(),l.getStatus(),l.getDecisionComment(),l.getCreatedAt());}
}
