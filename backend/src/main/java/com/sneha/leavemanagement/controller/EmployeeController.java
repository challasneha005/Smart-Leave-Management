package com.sneha.leavemanagement.controller;
import com.sneha.leavemanagement.dto.*; import com.sneha.leavemanagement.service.EmployeeService; import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/employees")
public class EmployeeController{
 private final EmployeeService service; public EmployeeController(EmployeeService s){service=s;}
 @PostMapping @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") public EmployeeResponse create(@Valid @RequestBody EmployeeRequest r,Authentication a){return service.create(r,a);}
 @GetMapping @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") public PageResponse<EmployeeResponse> list(@RequestParam(required=false) String search,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="10") int size,Authentication a){return service.search(search,page,size,a);}
 @GetMapping("/managers") @PreAuthorize("hasRole('ADMIN')") public java.util.List<EmployeeResponse> managers(){return service.managers();}
 @GetMapping("/{id}") public EmployeeResponse get(@PathVariable Long id,Authentication a){EmployeeResponse r=service.get(id); if(service.currentRole(a)!=com.sneha.leavemanagement.model.Role.ADMIN && service.currentRole(a)!=com.sneha.leavemanagement.model.Role.MANAGER && !r.username().equals(a.getName())) throw new com.sneha.leavemanagement.exception.BusinessException("Not allowed"); return r;}
 @PutMapping("/{id}") @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") public EmployeeResponse update(@PathVariable Long id,@Valid @RequestBody EmployeeUpdateRequest r,Authentication a){return service.update(id,r,a);}
 @DeleteMapping("/{id}") @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") public void delete(@PathVariable Long id,Authentication a){service.delete(id,a);}
}
