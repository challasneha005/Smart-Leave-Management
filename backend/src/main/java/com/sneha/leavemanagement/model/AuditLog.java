package com.sneha.leavemanagement.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="audit_logs")
public class AuditLog {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String username;
 @Column(nullable=false,length=80) private String action;
 @Column(nullable=false,length=80) private String entityType;
 @Column(nullable=false) private Long entityId;
 @Column(nullable=false) private LocalDateTime timestamp=LocalDateTime.now();
 @Column(length=500) private String description;
 public AuditLog(){} public AuditLog(String u,String a,String t,Long id,String d){username=u;action=a;entityType=t;entityId=id;description=d;}
 public Long getId(){return id;} public String getUsername(){return username;} public String getAction(){return action;} public String getEntityType(){return entityType;} public Long getEntityId(){return entityId;} public LocalDateTime getTimestamp(){return timestamp;} public String getDescription(){return description;}
}
