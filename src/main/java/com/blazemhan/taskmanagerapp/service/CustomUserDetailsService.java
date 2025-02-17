package com.blazemhan.taskmanagerapp.service;

import com.blazemhan.taskmanagerapp.model.CustomUserDetails;
import com.blazemhan.taskmanagerapp.model.User;
import com.blazemhan.taskmanagerapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;
@Component
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found"));

        if(Objects.isNull(user)){
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }
    }

