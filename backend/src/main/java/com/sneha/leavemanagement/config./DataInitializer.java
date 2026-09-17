package com.sneha.leavemanagement.config;
import com.sneha.leavemanagement.model.*; import com.sneha.leavemanagement.repository.*; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder; import java.time.LocalDate;
@Configuration public class DataInitializer {
 @Bean CommandLineRunner seed(DepartmentRepository d,LeaveTypeRepository t,UserRepository u,EmployeeRepository e,PasswordEncoder enc){return args->{
  Department eng=d.findByNameIgnoreCase("Engineering").orElseGet(()->d.save(new Department("Engineering")));
  d.findByNameIgnoreCase("HR").orElseGet(()->d.save(new Department("HR"))); d.findByNameIgnoreCase("Finance").orElseGet(()->d.save(new Department("Finance"))); d.findByNameIgnoreCase("Marketing").orElseGet(()->d.save(new Department("Marketing")));
  if(t.count()==0){t.save(new LeaveType("Casual",12));t.save(new LeaveType("Sick",10));t.save(new LeaveType("Annual",15));t.save(new LeaveType("Unpaid",365));}
  Employee admin=ensure(u,e,enc,"admin","Admin@123",Role.ADMIN,"System Admin","admin@company.local",eng,null);
  Employee manager2=ensure(u,e,enc,"manager2","Manager2@123",Role.MANAGER,"Manager Two","manager2@company.local",eng,null);
  Employee manager1=ensure(u,e,enc,"manager1","Manager1@123",Role.MANAGER,"Manager One","manager1@company.local",eng,manager2);
  ensure(u,e,enc,"employee","Employee@123",Role.EMPLOYEE,"Demo Employee","employee@company.local",eng,manager1);
  };
 }
 private Employee ensure(UserRepository u,EmployeeRepository e,PasswordEncoder enc,String username,String pass,Role role,String name,String email,Department dep,Employee manager){
  User user=u.findByUsername(username).orElseGet(()->u.save(new User(username,enc.encode(pass),role)));
  user.setRole(role); user.setEnabled(true); u.save(user);
  Employee emp=e.findByUserUsername(username).orElseGet(Employee::new);
  emp.setName(name);emp.setEmail(email);emp.setDepartment(dep);emp.setJoiningDate(emp.getJoiningDate()==null?LocalDate.now():emp.getJoiningDate());emp.setUser(user);emp.setManager(manager);emp.setActive(true);e.save(emp);
  user.setEmployee(emp);u.save(user);return emp;
 }
}
