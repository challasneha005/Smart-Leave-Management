package com.sneha.leavemanagement.service;
import com.sneha.leavemanagement.model.AuditLog; import com.sneha.leavemanagement.repository.AuditLogRepository; import org.springframework.security.core.Authentication; import org.springframework.stereotype.Service;
@Service public class AuditService{private final AuditLogRepository repo; public AuditService(AuditLogRepository repo){this.repo=repo;} public void log(Authentication a,String action,String type,Long id,String desc){repo.save(new AuditLog(a==null?"SYSTEM":a.getName(),action,type,id,desc));}}
