package com.sneha.leavemanagement.repository;
import com.sneha.leavemanagement.model.AuditLog; import org.springframework.data.domain.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface AuditLogRepository extends JpaRepository<AuditLog,Long>{Page<AuditLog> findAllByOrderByTimestampDesc(Pageable pageable);}
