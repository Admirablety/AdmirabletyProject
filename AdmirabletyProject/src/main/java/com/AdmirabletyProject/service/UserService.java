package com.AdmirabletyProject.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.AdmirabletyProject.model.Tracking;
import com.AdmirabletyProject.model.User;
import com.AdmirabletyProject.repository.TrackingRepository;
import com.AdmirabletyProject.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;
    private TrackingRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, 
                       TrackingRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
        
    public List<User> findAll(){
        return (List<User>) userRepository.findAll();
    }
        
    public void save(User user) {
        userRepository.save(user);
    }
    
    public User saveNewUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Tracking userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Tracking>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }
    
    public User getLoggedInUser() {
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        
        return findByUsername(loggedInUsername);
    }
}