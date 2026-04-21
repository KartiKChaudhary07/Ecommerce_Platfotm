package com.booknest.auth.controller;

import com.booknest.auth.entity.Role;
import com.booknest.auth.entity.User;
import com.booknest.auth.repository.UserRepository;
import com.booknest.auth.security.JwtUtils;
import com.booknest.auth.security.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody Map<String, String> loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.get("username"), loginRequest.get("password")));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
    String role = userDetails.getAuthorities().iterator().next().getAuthority();

    Map<String, Object> response = new HashMap<>();
    response.put("token", jwt);
    response.put("id", userDetails.getId());
    response.put("username", userDetails.getUsername());
    response.put("email", userDetails.getEmail());
    response.put("role", role);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody Map<String, String> signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.get("username"))) {
      return ResponseEntity.badRequest().body("Error: Username is already taken!");
    }

    if (userRepository.existsByEmail(signUpRequest.get("email"))) {
      return ResponseEntity.badRequest().body("Error: Email is already in use!");
    }

    // Create new user's account
    User user = new User();
    user.setUsername(signUpRequest.get("username"));
    user.setEmail(signUpRequest.get("email"));
    user.setPassword(encoder.encode(signUpRequest.get("password")));
    user.setFirstName(signUpRequest.get("firstName"));
    user.setLastName(signUpRequest.get("lastName"));
    
    String strRole = signUpRequest.get("role");
    if (strRole == null || strRole.equalsIgnoreCase("customer")) {
      user.setRole(Role.CUSTOMER);
    } else {
      user.setRole(Role.ADMIN);
    }

    userRepository.save(user);

    return ResponseEntity.ok("User registered successfully!");
  }
}
