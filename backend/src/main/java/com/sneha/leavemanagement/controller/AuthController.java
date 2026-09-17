package com.sneha.leavemanagement.controller;
import com.sneha.leavemanagement.dto.*; import com.sneha.leavemanagement.service.AuthService; import jakarta.servlet.http.*; import jakarta.validation.Valid; import org.springframework.security.core.*; import org.springframework.security.core.context.*; import org.springframework.security.web.context.HttpSessionSecurityContextRepository; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/auth")
public class AuthController{
 private final AuthService auth; private final HttpSessionSecurityContextRepository repo;
 public AuthController(AuthService a,HttpSessionSecurityContextRepository r){auth=a;repo=r;}
 @PostMapping("/login") public LoginResponse login(@Valid @RequestBody LoginRequest req,HttpServletRequest request,HttpServletResponse response){Authentication a=auth.authenticate(req);SecurityContext c=SecurityContextHolder.createEmptyContext();c.setAuthentication(a);SecurityContextHolder.setContext(c);repo.saveContext(c,request,response);return auth.response(a.getName());}
 @GetMapping("/me") public LoginResponse me(Authentication a){return auth.response(a.getName());}
}
