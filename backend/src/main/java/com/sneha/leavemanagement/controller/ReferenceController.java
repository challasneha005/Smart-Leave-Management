package com.sneha.leavemanagement.controller;
import com.sneha.leavemanagement.model.*; import com.sneha.leavemanagement.repository.*; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/api/reference") public class ReferenceController{
 private final DepartmentRepository d; private final LeaveTypeRepository t; public ReferenceController(DepartmentRepository d,LeaveTypeRepository t){this.d=d;this.t=t;}
 @GetMapping("/departments") public List<Department> departments(){return d.findAll();}
 @GetMapping("/leave-types") public List<LeaveType> types(){return t.findAll();}
 @PostMapping("/departments") @PreAuthorize("hasRole('ADMIN')") public Department addDepartment(@RequestBody Department x){return d.save(x);}
 @PostMapping("/leave-types") @PreAuthorize("hasRole('ADMIN')") public LeaveType addType(@RequestBody LeaveType x){return t.save(x);}
}
