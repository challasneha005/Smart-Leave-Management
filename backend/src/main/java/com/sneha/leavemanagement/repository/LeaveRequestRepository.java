package com.sneha.leavemanagement.repository;
import com.sneha.leavemanagement.model.*; import org.springframework.data.domain.*; import org.springframework.data.jpa.repository.*; import java.time.LocalDate; import java.util.*;
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest,Long>{
 List<LeaveRequest> findByStatus(LeaveStatus status);
 List<LeaveRequest> findByEmployeeIdOrderByCreatedAtDesc(Long employeeId);
 Page<LeaveRequest> findAllByOrderByCreatedAtDesc(Pageable pageable);
 Page<LeaveRequest> findByStatusOrderByCreatedAtDesc(LeaveStatus status,Pageable pageable);
 Page<LeaveRequest> findByEmployeeManagerIdOrderByCreatedAtDesc(Long managerId,Pageable pageable);
 Page<LeaveRequest> findByEmployeeManagerIdAndStatusOrderByCreatedAtDesc(Long managerId,LeaveStatus status,Pageable pageable);
 long countByStatus(LeaveStatus status);
 long countByEmployeeIdAndStatus(Long employeeId,LeaveStatus status);
 long countByEmployeeManagerId(Long managerId);
 long countByEmployeeManagerIdAndStatus(Long managerId,LeaveStatus status);
 @Query("select count(l)>0 from LeaveRequest l where l.employee.id=:employeeId and l.status in :statuses and l.startDate <= :endDate and l.endDate >= :startDate")
 boolean hasOverlap(Long employeeId,LocalDate startDate,LocalDate endDate,List<LeaveStatus> statuses);
}
