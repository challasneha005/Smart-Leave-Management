package com.sneha.leavemanagement.model;
import jakarta.persistence.*;
@Entity @Table(name="leave_types")
public class LeaveType {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true,length=50) private String name;
 @Column(nullable=false) private int annualLimit;
 public LeaveType(){} public LeaveType(String name,int limit){this.name=name;this.annualLimit=limit;} public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;} public int getAnnualLimit(){return annualLimit;} public void setAnnualLimit(int v){annualLimit=v;}
}
