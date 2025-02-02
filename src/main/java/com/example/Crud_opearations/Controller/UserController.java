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
//import com.example.Crud_opearations.util.JwtUtil;
import com.example.Crud_opearations.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    //private final AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    public UserController(AuthenticationManager authenticationManager, UserService userService , JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

//    @PostMapping("/login")
//    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody UserDTO userDTO) {
//        UserEntity user = userService.findUserByEmail(userDTO.getEmail());
//        System.out.println("user email id "+userDTO.getEmail());
//        System.out.println("password is ");
//        if (user != null && user.getPassword().equals(userDTO.getPassword())) {
//            System.out.println("inside if");
//            // Authenticate user
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword())
//            );
//
//            // Generate JWT token
//            //String token = jwtUtil.generateToken(userDTO.getEmail());
//            boolean isAdmin = user.isAdmin();
//
//            logger.info("User {} logged in successfully", userDTO.getEmail());
//
//            return ResponseEntity.ok(new AuthResponse("User Login Successfully done", isAdmin, token));
//        } else {
//            logger.warn("Invalid login attempt for email: {}", userDTO.getEmail());
//            return ResponseEntity.status(401).body(new AuthResponse("Invalid email or password", false, null));
//        }
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<AuthResponse> logoutUser() {
//        logger.info("User logged out successfully");
//        return ResponseEntity.ok(new AuthResponse("User logged out successfully", false, null));
//    }

    @PostMapping("register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO user){
        System.out.println("admin value is "+user.isAdmin());
        UserDTO addedEmployee =  userService.registerUser(user);
        return ResponseEntity.ok(addedEmployee);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserDTO user){
        //after verifying username and password it generates a token that is done by below code ie. Authentication
        boolean isAdmin = user.isAdmin();
        System.out.println("admin value inside login is "+user.isAdmin());

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
                    // Generate JWT token
            String token = jwtUtil.generateToken(user.getEmail());


            logger.info("User {} logged in successfully", user.getEmail());
        if (authentication.isAuthenticated()){
            return ResponseEntity.ok(new AuthResponse("User Login Successfully done", isAdmin,token));
        }else{
            return ResponseEntity.ok(new AuthResponse("Invalid Credentials" , isAdmin , token));
        }
    }

    @GetMapping("hello")
    public String greet(){
        return "Hello world";
    }
}
