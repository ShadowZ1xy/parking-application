package com.company.parking.controller;

import com.company.parking.model.User;
import com.company.parking.model.dto.UserDto;
import com.company.parking.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('admin')")
    public Iterable<UserDto> getUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public UserDto getUser(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping("/registration")
    public ResponseEntity<String> newUserRegistration(@RequestBody User user) {
        userService.newUserRegistration(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
