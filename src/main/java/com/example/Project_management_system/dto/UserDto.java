package com.example.Project_management_system.dto;

import com.example.Project_management_system.enums.UserRole;
import lombok.Data;

import java.time.LocalDate;


@Data
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private UserRole userRole;
}
