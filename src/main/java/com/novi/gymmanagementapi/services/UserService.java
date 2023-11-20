package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.dto.AuthenticationRequestDto;
import com.novi.gymmanagementapi.exceptions.EmailAlreadyTakenException;
import com.novi.gymmanagementapi.models.User;
import com.novi.gymmanagementapi.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createNewUser(AuthenticationRequestDto newUser) {
        Optional<User> optionalUser = userRepository.findById(newUser.getEmail());
        if (optionalUser.isEmpty()) {
            User user = new User();
            user.setEmail(newUser.getEmail());
            user.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
            user.setEnabled(true);
            userRepository.save(user);

        } else {
            throw new EmailAlreadyTakenException(newUser.getEmail());
        }
    }
}
