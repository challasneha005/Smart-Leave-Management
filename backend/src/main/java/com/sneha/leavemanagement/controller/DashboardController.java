package com.sneha.leavemanagement.controller;
import com.sneha.leavemanagement.dto.DashboardResponse; import com.sneha.leavemanagement.model.*; import com.sneha.leavemanagement.repository.*; import com.sneha.leavemanagement.service.EmployeeService; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/dashboard") public class DashboardController{
 private final EmployeeRepository employees; private final LeaveRequestRepository leaves; private final EmployeeService employeeService;
 public DashboardController(EmployeeRepository e,LeaveRequestRepository l,EmployeeService s){employees=e;leaves=l;employeeService=s;}
 @GetMapping public DashboardResponse dashboard(Authentication a){
  var e=employeeService.getByUsername(a.getName()); var role=e.getUser().getRole();
  if(role==Role.ADMIN) return new DashboardResponse(employees.count(),leaves.countByStatus(LeaveStatus.PENDING),leaves.countByStatus(LeaveStatus.APPROVED),leaves.countByStatus(LeaveStatus.REJECTED),e.getLeaveBalance(),leaves.countByEmployeeIdAndStatus(e.getId(),LeaveStatus.PENDING),leaves.countByEmployeeIdAndStatus(e.getId(),LeaveStatus.APPROVED),employees.count(),leaves.countByStatus(LeaveStatus.PENDING));
  long team=employees.countByManagerId(e.getId()), pending=leaves.countByEmployeeManagerIdAndStatus(e.getId(),LeaveStatus.PENDING);
  return new DashboardResponse(employees.count(),leaves.countByStatus(LeaveStatus.PENDING),leaves.countByStatus(LeaveStatus.APPROVED),leaves.countByStatus(LeaveStatus.REJECTED),e.getLeaveBalance(),leaves.countByEmployeeIdAndStatus(e.getId(),LeaveStatus.PENDING),leaves.countByEmployeeIdAndStatus(e.getId(),LeaveStatus.APPROVED),team,pending);
 }
}
