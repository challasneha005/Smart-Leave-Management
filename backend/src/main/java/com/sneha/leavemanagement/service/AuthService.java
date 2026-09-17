package com.sneha.leavemanagement.service;
import com.sneha.leavemanagement.dto.*; import com.sneha.leavemanagement.model.User; import com.sneha.leavemanagement.repository.UserRepository; import org.springframework.security.authentication.*; import org.springframework.security.core.Authentication; import org.springframework.security.core.userdetails.UsernameNotFoundException; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.stereotype.Service;
@Service public class AuthService{
 private final UserRepository users; private final AuthenticationManager manager; private final PasswordEncoder encoder;
 public AuthService(UserRepository users,AuthenticationManager manager,PasswordEncoder encoder){this.users=users;this.manager=manager;this.encoder=encoder;}
 public Authentication authenticate(LoginRequest req){return manager.authenticate(new UsernamePasswordAuthenticationToken(req.username(),req.password()));}
 public LoginResponse response(String username){User u=users.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found")); var e=u.getEmployee(); return new LoginResponse(u.getId(),u.getUsername(),u.getRole().name(),e==null?null:e.getId(),e==null?null:e.getName());}
 public String encode(String raw){return encoder.encode(raw);}
}
