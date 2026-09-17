package com.sneha.leavemanagement.repository;
import com.sneha.leavemanagement.model.LeaveType; import org.springframework.data.jpa.repository.JpaRepository; import java.util.Optional;
public interface LeaveTypeRepository extends JpaRepository<LeaveType,Long>{Optional<LeaveType> findByNameIgnoreCase(String name);}
