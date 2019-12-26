package com.thebest.services;

import com.thebest.app.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("authService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.loadByEmail(email);
        if(userDetails == null) {
            throw new UsernameNotFoundException("User " + email+ " not found");
        }
        UserDetails user = User.withUserDetails(userDetails)
                .password(userDetails.getPassword()).build();
        return user;
    }
}
