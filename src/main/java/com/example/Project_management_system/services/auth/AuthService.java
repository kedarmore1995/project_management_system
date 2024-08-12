package com.example.Project_management_system.services.auth;

import com.example.Project_management_system.dto.SignupRequest;
import com.example.Project_management_system.dto.UserDto;

public interface AuthService {
    UserDto signupUser(SignupRequest signupRequest);

    boolean hasUserWithEmail(String email);

}
