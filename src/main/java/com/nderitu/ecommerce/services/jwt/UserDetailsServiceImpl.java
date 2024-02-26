package com.nderitu.ecommerce.services.jwt;

import com.nderitu.ecommerce.entity.User;
import com.nderitu.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired //automatically wire beans together

    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser=userRepository.findByEmail(username);

        //check if user is empty and return user not found exception otherwise return a new user

        if(optionalUser.isEmpty()) throw new UsernameNotFoundException("Username not found,null");
        return new org.springframework.security.core.userdetails.User(optionalUser.get().getEmail(),
                optionalUser.get().getPassword(),new ArrayList<>());//new user
    }
}

