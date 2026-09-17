package com.sneha.leavemanagement.config;
import com.sneha.leavemanagement.security.CustomUserDetailsService; import org.springframework.context.annotation.*; import org.springframework.security.authentication.*; import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; import org.springframework.security.config.annotation.web.builders.HttpSecurity; import org.springframework.security.config.http.SessionCreationPolicy; import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.security.web.SecurityFilterChain; import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
@Configuration
@org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
public class SecurityConfig{
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean AuthenticationManager authenticationManager(AuthenticationConfiguration c)throws Exception{return c.getAuthenticationManager();}
 @Bean SecurityFilterChain filterChain(HttpSecurity http, CustomUserDetailsService uds)throws Exception{
  http.csrf(csrf->csrf.disable())
   .cors(c-> {})
   .userDetailsService(uds)
   .authorizeHttpRequests(a->a.requestMatchers("/api/auth/**","/swagger-ui/**","/swagger-ui.html","/v3/api-docs/**","/","/index.html","/style.css","/app.js").permitAll().anyRequest().authenticated())
   .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).maximumSessions(2))
   .logout(l->l.logoutUrl("/api/auth/logout").logoutSuccessHandler((req,res,auth)->res.setStatus(204)).invalidateHttpSession(true).deleteCookies("JSESSIONID"));
  return http.build();
 }
 @Bean HttpSessionSecurityContextRepository securityContextRepository(){return new HttpSessionSecurityContextRepository();}
}
