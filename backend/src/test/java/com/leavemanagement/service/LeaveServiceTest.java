package com.sneha.leavemanagement.service;

import com.sneha.leavemanagement.dto.LeaveRequestDto;
import com.sneha.leavemanagement.exception.BusinessException;
import com.sneha.leavemanagement.model.*;
import com.sneha.leavemanagement.repository.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LeaveServiceTest {
 @Mock LeaveRequestRepository leaves; @Mock EmployeeRepository employees; @Mock LeaveTypeRepository types; @Mock AuditService audit; @Mock Authentication authentication;
 LeaveService service;
 @BeforeEach void setUp(){MockitoAnnotations.openMocks(this);service=new LeaveService(leaves,employees,types,audit);when(authentication.getName()).thenReturn("employee");doReturn((Object) List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_EMPLOYEE"))).when(authentication).getAuthorities();}
 @Test void rejectsPastStartDate(){Employee e=new Employee();e.setName("Test");e.setLeaveBalance(20);User u=new User("employee","x",Role.EMPLOYEE);e.setUser(u);when(employees.findById(1L)).thenReturn(Optional.of(e));var dto=new LeaveRequestDto(1L,LocalDate.now().minusDays(1),LocalDate.now(),"Medical appointment");assertThrows(BusinessException.class,()->service.apply(1L,dto,authentication));verify(leaves,never()).save(any());}
 @Test void rejectsOverlap(){Employee e=new Employee();e.setLeaveBalance(20);User u=new User("employee","x",Role.EMPLOYEE);e.setUser(u);when(employees.findById(1L)).thenReturn(Optional.of(e));when(leaves.hasOverlap(eq(1L),any(),any(),any())).thenReturn(true);var dto=new LeaveRequestDto(1L,LocalDate.now(),LocalDate.now().plusDays(1),"Personal reason");assertThrows(BusinessException.class,()->service.apply(1L,dto,authentication));}
}
