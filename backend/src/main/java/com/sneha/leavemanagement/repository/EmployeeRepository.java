package com.sneha.leavemanagement.repository;
import com.sneha.leavemanagement.model.*; import org.springframework.data.domain.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface EmployeeRepository extends JpaRepository<Employee,Long>{
 Optional<Employee> findByEmail(String email); Optional<Employee> findByUserUsername(String username);
 boolean existsByEmail(String email);
 Page<Employee> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name,String email,Pageable pageable);
 Page<Employee> findByManagerId(Long managerId,Pageable pageable);
 List<Employee> findByManagerIdAndActiveTrue(Long managerId);
 long countByManagerId(Long managerId);
}
