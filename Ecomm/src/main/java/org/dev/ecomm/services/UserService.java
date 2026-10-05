package org.dev.ecomm.services;

import org.dev.ecomm.model.User;
import org.dev.ecomm.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        User newUser = userRepository.save(user);
        System.out.println("New User: " + newUser + " added..");
        return newUser;
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if( user!=null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
