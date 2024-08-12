package com.example.Project_management_system.model;

import com.example.Project_management_system.dto.UserDto;
import com.example.Project_management_system.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private LocalDate date_Of_Birth;
    private String location;
    private String gender;
    private Long mobile_number;
    private String password;
    private UserRole userRole;

//    public User(UserDto userDto){
//        this.name = userDto.name;
//        this.email = userDto.email;
//        this.password = userDto.password;
//        this.mobile_number = userDto.mobile_number;;
//    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserDto getUserDto(){
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setEmail(email);
        userDto.setName(name);
        userDto.setUserRole(userRole);
        return userDto;
    }

}
