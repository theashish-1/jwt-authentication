package com.example.Crud_opearations.Service;

import com.example.Crud_opearations.DTO.UserDTO;
import com.example.Crud_opearations.Entity.UserEntity;
import com.example.Crud_opearations.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

//    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
//
//    public boolean authenticate(String email, String password) {
//        UserEntity userOptional = userRepository.findByEmail(email);
//
//        return userOptional.getPassword().equals(password);
//    }


    @Autowired
    private BCryptPasswordEncoder encoder;

    public boolean authenticate(String email, String password) {
        UserEntity user = userRepository.findByEmail(email);

        if (user == null) {
            return false;
        }

        return encoder.matches(password, user.getPassword()); //  Compare using BCrypt
    }




    public UserEntity findUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        System.out.println("Admin value reterived from db is  "+user.isAdmin());
        return userRepository.findByEmail(email);
    }

    public UserDTO registerUser(UserDTO user) {
        UserEntity employee = new UserEntity();
        employee.setEmail(user.getEmail());
        employee.setFirstName(user.getFirstName());
        employee.setLastName(user.getLastName());
        //encoding password
        String encodedPassword = encoder.encode(user.getPassword());
        System.out.println("Encrypted password is "+encodedPassword);
        employee.setPassword(encodedPassword);
//        employee.setAdmin(user.isAdmin());
        employee.setAdmin(user.getAdmin());
        System.out.println("admin value before saving to db"+user.getAdmin());

        UserEntity savedUser =  userRepository.save(employee);

        UserDTO savedUserDTO = new UserDTO();

        savedUserDTO.setFirstName(savedUser.getFirstName());
        savedUserDTO.setLastName(savedUser.getLastName());
        savedUserDTO.setEmail(savedUser.getEmail());
//        savedUserDTO.setAdmin(savedUser.isAdmin());
        savedUserDTO.setAdmin(savedUser.isAdmin());
        savedUserDTO.setPassword(savedUser.getPassword());
        return savedUserDTO;

    }
}
