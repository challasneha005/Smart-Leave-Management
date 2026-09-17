package com.sneha.leavemanagement.model;
import jakarta.persistence.*; import jakarta.validation.constraints.*; import java.time.LocalDate;
@Entity @Table(name="employees")
public class Employee {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @NotBlank @Size(max=100) @Column(nullable=false) private String name;
 @Email @NotBlank @Column(nullable=false,unique=true) private String email;
 @ManyToOne(optional=false) @JoinColumn(name="department_id") private Department department;
 @Column(nullable=false) private int leaveBalance=20;
 @Column(nullable=false) private LocalDate joiningDate=LocalDate.now();
 @Column(nullable=false) private boolean active=true;
 @OneToOne @JoinColumn(name="user_id",unique=true) private User user;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="manager_id") private Employee manager;

 public Employee(){}
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public Department getDepartment(){return department;} public void setDepartment(Department v){department=v;}
 public int getLeaveBalance(){return leaveBalance;} public void setLeaveBalance(int v){leaveBalance=v;}
 public LocalDate getJoiningDate(){return joiningDate;} public void setJoiningDate(LocalDate v){joiningDate=v;}
 public boolean isActive(){return active;} public void setActive(boolean v){active=v;}
 public User getUser(){return user;} public void setUser(User v){user=v;}
 public Employee getManager(){return manager;} public void setManager(Employee v){manager=v;}
}
