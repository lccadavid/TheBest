package com.thebest.services;

import com.thebest.app.dao.UserRepository;
import com.thebest.app.exceptions.DomainLogicException;
import com.thebest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User register(User newUser) throws DomainLogicException {
        try {
            User user = userRepository.save(newUser);
            return user;
        } catch (Exception e) {
            throw new DomainLogicException("Error saving user");
        }
    }
}
