package com.wiprojobsearch.joblisting.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wiprojobsearch.joblisting.model.LoginRequest;
import com.wiprojobsearch.joblisting.model.User;
import com.wiprojobsearch.joblisting.model.UserLoginResponse;
import com.wiprojobsearch.joblisting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserAuthnOperationService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private AuthenticationManager authenticationManager;


    public User getByEmail(String username){
        return userRepository.findByEmail(username);
    }



    public UserLoginResponse loginUser(LoginRequest loginRequest){

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(loginRequest.getEmail());

       if(user == null){
         throw  new UsernameNotFoundException("User not found with username or email:" + loginRequest.getEmail());
       }


        return  new UserLoginResponse(user.getId(), user.getEmail(), user.getEmail(), user.getMobileNumber());

    }
}
