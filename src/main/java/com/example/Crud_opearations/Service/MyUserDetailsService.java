package com.example.Crud_opearations.Service;

import com.example.Crud_opearations.Entity.UserEntity;
import com.example.Crud_opearations.Entity.UserPrincipal;
import com.example.Crud_opearations.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user =  userRepository.findByEmail(username);
        if(user==null){
            System.out.println("User Not found");
            throw new UsernameNotFoundException("User Not Found");
        }
        return new UserPrincipal(user);

    }
}
