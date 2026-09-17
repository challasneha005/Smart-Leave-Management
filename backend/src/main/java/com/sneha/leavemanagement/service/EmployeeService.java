package com.sneha.leavemanagement.service;
import com.sneha.leavemanagement.dto.*; import com.sneha.leavemanagement.exception.*; import com.sneha.leavemanagement.model.*;
import com.sneha.leavemanagement.repository.*; import org.springframework.data.domain.*; import org.springframework.security.core.Authentication; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
@Service @Transactional
public class EmployeeService {
 private final EmployeeRepository employees; private final DepartmentRepository departments; private final UserRepository users; private final AuthService auth; private final AuditService audit;
 public EmployeeService(EmployeeRepository e,DepartmentRepository d,UserRepository u,AuthService a,AuditService audit){employees=e;departments=d;users=u;auth=a;this.audit=audit;}

 public EmployeeResponse create(EmployeeRequest r, Authentication a){
  Role role=parseRole(r.role()); Role creator=currentRole(a);
  if(creator==Role.MANAGER && role!=Role.EMPLOYEE) throw new BusinessException("Managers can add employees only");
  if(creator!=Role.ADMIN && creator!=Role.MANAGER) throw new BusinessException("Not allowed");
  if(employees.existsByEmail(r.email())) throw new BusinessException("Email already exists");
  if(users.existsByUsername(r.username())) throw new BusinessException("Username already exists");
  Department d=departments.findById(r.departmentId()).orElseThrow(()->new ResourceNotFoundException("Department not found"));
  Employee manager;
  if(creator==Role.MANAGER) manager=getByUsername(a.getName());
  else manager=r.managerId()==null?null:employees.findById(r.managerId()).orElseThrow(()->new ResourceNotFoundException("Manager not found"));
  if(manager!=null && manager.getUser()!=null && manager.getUser().getRole()!=Role.MANAGER) throw new BusinessException("Selected manager must have MANAGER role");
  User u=new User(r.username(),auth.encode(r.password()),role); users.save(u);
  Employee e=new Employee(); e.setName(r.name()); e.setEmail(r.email()); e.setDepartment(d); e.setJoiningDate(r.joiningDate()); e.setUser(u); e.setManager(manager); employees.save(e);
  u.setEmployee(e); users.save(u);
  audit.log(a,"CREATE","EMPLOYEE",e.getId(),"Created "+role+" "+e.getName()+" under "+(manager==null?"no manager":manager.getName()));
  return toResponse(e);
 }
 public EmployeeResponse get(Long id){return toResponse(getEntity(id));}
 public PageResponse<EmployeeResponse> search(String q,int page,int size,Authentication a){
  Pageable p=PageRequest.of(Math.max(0,page),Math.min(Math.max(1,size),50),Sort.by("name").ascending());
  Page<Employee> result;
  if(currentRole(a)==Role.MANAGER){
   Long mid=getByUsername(a.getName()).getId();
   result=(q==null||q.isBlank())?employees.findByManagerId(mid,p):employees.findByManagerId(mid,p); // team search kept simple
  } else result=(q==null||q.isBlank())?employees.findAll(p):employees.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(q,q,p);
  return new PageResponse<>(result.map(this::toResponse).getContent(),result.getNumber(),result.getSize(),result.getTotalElements(),result.getTotalPages());
 }
 public EmployeeResponse update(Long id,EmployeeUpdateRequest r,Authentication a){
  Employee e=getEntity(id); authorizeManagerScope(e,a);
  if(!e.getEmail().equalsIgnoreCase(r.email())&&employees.findByEmail(r.email()).isPresent())throw new BusinessException("Email already exists");
  e.setName(r.name());e.setEmail(r.email());e.setDepartment(departments.findById(r.departmentId()).orElseThrow(()->new ResourceNotFoundException("Department not found")));e.setActive(r.active());
  audit.log(a,"UPDATE","EMPLOYEE",id,"Updated employee");return toResponse(e);
 }
 public void delete(Long id,Authentication a){Employee e=getEntity(id);authorizeManagerScope(e,a);e.setActive(false);if(e.getUser()!=null)e.getUser().setEnabled(false);audit.log(a,"DEACTIVATE","EMPLOYEE",id,"Deactivated employee");}
 public Employee getEntity(Long id){return employees.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not found"));}
 public Employee getByUsername(String username){return employees.findByUserUsername(username).orElseThrow(()->new ResourceNotFoundException("Employee profile not found"));}
 public Role currentRole(Authentication a){return users.findByUsername(a.getName()).orElseThrow(()->new ResourceNotFoundException("User not found")).getRole();}
 public void authorizeManagerScope(Employee e,Authentication a){if(currentRole(a)==Role.ADMIN)return; if(currentRole(a)==Role.MANAGER){Employee m=getByUsername(a.getName());if(e.getManager()==null||!e.getManager().getId().equals(m.getId()))throw new BusinessException("You can manage only your direct reports");return;}throw new BusinessException("Not allowed");}
 public java.util.List<EmployeeResponse> managers(){return employees.findAll().stream().filter(e->e.isActive()&&e.getUser()!=null&&e.getUser().getRole()==Role.MANAGER).map(this::toResponse).toList();}
 private Role parseRole(String r){try{return r==null||r.isBlank()?Role.EMPLOYEE:Role.valueOf(r.toUpperCase());}catch(Exception ex){throw new BusinessException("Role must be EMPLOYEE, MANAGER or ADMIN");}}
 private EmployeeResponse toResponse(Employee e){User u=e.getUser();Employee m=e.getManager();return new EmployeeResponse(e.getId(),e.getName(),e.getEmail(),e.getDepartment().getName(),e.getLeaveBalance(),e.getJoiningDate(),e.isActive(),u==null?null:u.getUsername(),u==null?null:u.getRole().name(),m==null?null:m.getId(),m==null?null:m.getName());}
}
