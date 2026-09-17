package com.sneha.leavemanagement.model;
import jakarta.persistence.*;
@Entity @Table(name="departments")
public class Department {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true,length=80) private String name;
 public Department(){} public Department(String name){this.name=name;} public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
}
