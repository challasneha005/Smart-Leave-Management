# Architecture & Design Notes

```text
Browser (HTML/CSS/JavaScript)
          |
       REST API
          |
  Controllers + Security
          |
       Services
          |
 DTOs / Validation / Rules
          |
    Spring Data JPA
          |
         MySQL
```

## Core domain
- User -> authentication identity and role
- Employee -> employee profile, department, balance
- Department -> normalized department reference
- LeaveType -> normalized leave category and annual limit
- LeaveRequest -> leave workflow and dates
- AuditLog -> traceable business actions

## Business rules
1. Employees can submit/cancel only their own leave.
2. Managers/Admins can approve or reject pending requests.
3. Leave cannot start in the past.
4. End date cannot precede start date.
5. Pending/approved requests cannot overlap.
6. Approval deducts the number of requested days.
7. Cancelling approved leave restores the balance.
8. Only active employees can apply for leave.
9. Email and username are unique.

## Security
Session-based Spring Security authentication is used so the project stays within the Java/Spring skill set. Passwords are BCrypt-hashed. API methods are protected with role-based `@PreAuthorize` rules.
