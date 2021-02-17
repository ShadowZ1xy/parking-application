package com.company.parking.service.user.impl;

import com.company.parking.exceptions.EntityNotFoundException;
import com.company.parking.exceptions.user.UserRegistrationException;
import com.company.parking.model.User;
import com.company.parking.model.dto.UserDto;
import com.company.parking.repository.UserRepository;
import com.company.parking.service.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Check fields with validation method.
     * If validation is passed: encrypt password and save user in db
     *
     * @throws UserRegistrationException if validation failed in {@link #userFieldsValidation(User)}
     */
    @Override
    public void newUserRegistration(User user) {
        userFieldsValidation(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public Iterable<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getById(Long id) {
        return new UserDto(
                userRepository.findById(id)
                        .orElseThrow(EntityNotFoundException::new));
    }

    /**
     * @throws UserRegistrationException if one or many fields fail validation.
     */
    private void userFieldsValidation(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new UserRegistrationException();
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new UserRegistrationException();
        }
        Optional<User> optionalUser = userRepository.getByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            throw new UserRegistrationException();
        }
    }
}
