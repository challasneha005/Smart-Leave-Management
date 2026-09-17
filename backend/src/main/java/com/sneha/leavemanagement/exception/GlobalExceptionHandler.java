package com.sneha.leavemanagement.exception;
import jakarta.servlet.http.HttpServletRequest; import org.springframework.http.*; import org.springframework.security.authentication.BadCredentialsException; import org.springframework.validation.FieldError; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import java.time.LocalDateTime; import java.util.stream.Collectors;
@RestControllerAdvice
public class GlobalExceptionHandler{
 private ResponseEntity<ApiError> error(HttpStatus s,String msg,HttpServletRequest r,java.util.Map<String,String> m){return ResponseEntity.status(s).body(new ApiError(LocalDateTime.now(),s.value(),s.getReasonPhrase(),msg,r.getRequestURI(),m));}
 @ExceptionHandler(ResourceNotFoundException.class) ResponseEntity<ApiError> notFound(ResourceNotFoundException e,HttpServletRequest r){return error(HttpStatus.NOT_FOUND,e.getMessage(),r,null);}
 @ExceptionHandler(BusinessException.class) ResponseEntity<ApiError> business(BusinessException e,HttpServletRequest r){return error(HttpStatus.BAD_REQUEST,e.getMessage(),r,null);}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ApiError> validation(MethodArgumentNotValidException e,HttpServletRequest r){var m=e.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage,(a,b)->a));return error(HttpStatus.BAD_REQUEST,"Validation failed",r,m);}
 @ExceptionHandler(BadCredentialsException.class) ResponseEntity<ApiError> creds(BadCredentialsException e,HttpServletRequest r){return error(HttpStatus.UNAUTHORIZED,"Invalid username or password",r,null);}
 @ExceptionHandler(Exception.class) ResponseEntity<ApiError> generic(Exception e,HttpServletRequest r){return error(HttpStatus.INTERNAL_SERVER_ERROR,"Unexpected server error",r,null);}
}
