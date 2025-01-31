package com.example.Crud_opearations.Service;

import com.example.Crud_opearations.Entity.UserEntity;
import com.example.Crud_opearations.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean authenticate(String email, String password) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);

        return userOptional.isPresent() && userOptional.get().getPassword().equals(password);
    }

    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null); // Return null if user is not found
    }
}
