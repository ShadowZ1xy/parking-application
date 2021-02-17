package com.company.parking.model.dto;

import com.company.parking.model.User;

public class UserDto {
    private Long id;
    private String username;
    private String publicName;

    public UserDto() {
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.publicName = user.getPublicName();
    }

    public String getUsername() {
        return username;
    }

    public String getPublicName() {
        return publicName;
    }

    public Long getId() {
        return id;
    }
}
