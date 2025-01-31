//package com.example.Crud_opearations.Controller;
//
//import com.example.Crud_opearations.DTO.AuthResponse;
//import com.example.Crud_opearations.DTO.UserDTO;
//import com.example.Crud_opearations.Entity.UserEntity;
//import com.example.Crud_opearations.Service.UserService;
//import com.example.Crud_opearations.util.JwtUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.CachingUserDetailsService;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("api/auth")
//@CrossOrigin(origins = "http://localhost:4200/")
//public class UserController {




    //    @PostMapping("/login")
//    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody UserDTO userDTO) {
//
//        UserEntity user = userService.findUserByEmail(userDTO.getEmail());
//
//        if (user != null && user.getPassword().equals(userDTO.getPassword())) {
//            // Authentication successful; check admin status
//            boolean isAdmin = user.isAdmin();
//
//
//            return ResponseEntity.ok(new AuthResponse("User Login Successfully done", isAdmin));
//        } else {
//            // Authentication failed
//            return ResponseEntity.status(401).body(new AuthResponse("Invalid email or password", false));
//        }
//    }

//}



package com.example.Crud_opearations.Controller;

import com.example.Crud_opearations.DTO.AuthResponse;
import com.example.Crud_opearations.DTO.UserDTO;
import com.example.Crud_opearations.Entity.UserEntity;
import com.example.Crud_opearations.Service.UserService;
import com.example.Crud_opearations.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "http://localhost:4200/")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public UserController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody UserDTO userDTO) {
        UserEntity user = userService.findUserByEmail(userDTO.getEmail());
        System.out.println("user email id "+userDTO.getEmail());
        if (user != null && user.getPassword().equals(userDTO.getPassword())) {
            // Authenticate user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword())
            );

            // Generate JWT token
            String token = jwtUtil.generateToken(userDTO.getEmail());
            boolean isAdmin = user.isAdmin();

            logger.info("User {} logged in successfully", userDTO.getEmail());

            return ResponseEntity.ok(new AuthResponse("User Login Successfully done", isAdmin, token));
        } else {
            logger.warn("Invalid login attempt for email: {}", userDTO.getEmail());
            return ResponseEntity.status(401).body(new AuthResponse("Invalid email or password", false, null));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logoutUser() {
        logger.info("User logged out successfully");
        return ResponseEntity.ok(new AuthResponse("User logged out successfully", false, null));
    }
}
