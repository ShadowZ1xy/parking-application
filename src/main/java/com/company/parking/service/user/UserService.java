package com.company.parking.service.user;

import com.company.parking.model.User;
import com.company.parking.model.dto.UserDto;

public interface UserService {
    void newUserRegistration(User user);

    Iterable<UserDto> getAll();

    UserDto getById(Long id);
}
