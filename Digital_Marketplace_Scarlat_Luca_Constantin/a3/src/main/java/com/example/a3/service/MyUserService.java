package com.example.a3.service;

import com.example.a3.model.Role;
import com.example.a3.model.User;
import com.example.a3.repository.RoleRepository;
import com.example.a3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class MyUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    private PasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    public User findUserByUserName(String userName) {
        return userRepository.findUserByUsername(userName);
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder().encode(user.getPassword()));

        Role userRole = roleRepository.findByRole("BUYER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        if(userRepository.count()==0){
            Role roleAdmin=roleRepository.findByRole("ADMIN");
            user.setRoles(new HashSet<Role>(Arrays.asList(userRole,roleAdmin)));
        }
        return userRepository.save(user);
    }

    public void becomeSeller(User user){
        Set<Role> roles=user.getRoles();
        roles.add(roleRepository.findByRole("PRODUCT_SELLER"));
        user.setRoles(roles);
        userRepository.save(user);
    }
}
