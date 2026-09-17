package com.sneha.leavemanagement.repository;
import com.sneha.leavemanagement.model.Department; import org.springframework.data.jpa.repository.JpaRepository; import java.util.Optional;
public interface DepartmentRepository extends JpaRepository<Department,Long>{Optional<Department> findByNameIgnoreCase(String name);}
