package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.dto.AuthenticationRequestDto;
import com.novi.gymmanagementapi.dto.UserDto;
import com.novi.gymmanagementapi.exceptions.EmailAlreadyTakenException;
import com.novi.gymmanagementapi.exceptions.EmailNotFoundException;
import com.novi.gymmanagementapi.models.Authority;
import com.novi.gymmanagementapi.models.User;
import com.novi.gymmanagementapi.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUserAccount(AuthenticationRequestDto newUser) {
        Optional<User> ou = userRepository.findById(newUser.getEmail());
        if (ou.isEmpty()) {
            User user = new User();
            user.setEmail(newUser.getEmail());
            user.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
            user.setEnabled(true);
            userRepository.save(user);
            addAuthority(user.getEmail(), "ROLE_MEMBER");

        } else {
            throw new EmailAlreadyTakenException(newUser.getEmail());
        }
    }

    public UserDto getUserAccount(String email) {
        Optional<User> ou = userRepository.findById(email);
        if (ou.isPresent()) {
            return toDTO(ou.get());

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public void updateUserAccount(String email, UserDto u) {
        Optional<User> ou = userRepository.findById(email);
        if (ou.isPresent()) {
            User user = ou.get();
            user.setEmail(email);
            user.setFirstname(u.getFirstname().toLowerCase());
            user.setLastname(u.getLastname().toLowerCase());
            user.setDateOfBirth(u.getDateOfBirth());
            userRepository.save(user);

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public Set<Authority> getAuthorities(String email) {
/*        if (!userRepository.existsById(email)) throw new EmailNotFoundException(email);
        User user = userRepository.findById(email).get();
        UserDto userDto = fromUser(user);
        return userDto.getAuthorities();*/
        return null;
    }

    public void addAuthority(String email, String authority) {

        if (!userRepository.existsById(email)) throw new EmailNotFoundException(email);
        User user = userRepository.findById(email).get();
        user.addAuthority(new Authority(email, authority));
        userRepository.save(user);
    }

    public void removeAuthority(String email, String authority) {
        if (!userRepository.existsById(email)) throw new EmailNotFoundException(email);
        User user = userRepository.findById(email).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }

    private UserDto toDTO(User model) {
        UserDto dto = new UserDto();
        dto.setEmail(model.getEmail());
        dto.setFirstname(model.getFirstname());
        dto.setLastname(model.getLastname());
        dto.setDateOfBirth(model.getDateOfBirth());
        return dto;
    }
}
