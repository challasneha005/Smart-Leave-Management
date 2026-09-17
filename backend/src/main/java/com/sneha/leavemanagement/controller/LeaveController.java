package com.sneha.leavemanagement.controller;
import com.sneha.leavemanagement.dto.*; import com.sneha.leavemanagement.model.LeaveStatus; import com.sneha.leavemanagement.service.*; import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/leaves")
public class LeaveController{
 private final LeaveService leaves; private final EmployeeService employees; public LeaveController(LeaveService l,EmployeeService e){leaves=l;employees=e;}
 @PostMapping("/employee/{employeeId}") public LeaveResponse apply(@PathVariable Long employeeId,@Valid @RequestBody LeaveRequestDto d,Authentication a){return leaves.apply(employeeId,d,a);}
 @GetMapping @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") public PageResponse<LeaveResponse> list(@RequestParam(required=false) LeaveStatus status,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="50") int size,Authentication a){return leaves.list(status,page,size,a);}
 @GetMapping("/mine") public java.util.List<LeaveResponse> mine(Authentication a){return leaves.mine(employees.getByUsername(a.getName()).getId());}
 @PutMapping("/{id}/approve") @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") public LeaveResponse approve(@PathVariable Long id,@RequestBody(required=false) DecisionRequest d,Authentication a){return leaves.decide(id,true,d==null?null:d.comment(),a);}
 @PutMapping("/{id}/reject") @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") public LeaveResponse reject(@PathVariable Long id,@RequestBody(required=false) DecisionRequest d,Authentication a){return leaves.decide(id,false,d==null?null:d.comment(),a);}
 @PutMapping("/{id}/cancel") public LeaveResponse cancel(@PathVariable Long id,Authentication a){return leaves.cancel(id,a);}
}
