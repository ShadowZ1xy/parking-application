package com.company.parking.util;

import com.company.parking.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class UserUtil {
    public User currentUser() {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();
        return (User) auth.getPrincipal();
    }
}
