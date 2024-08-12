package com.example.Project_management_system.repo;

import com.example.Project_management_system.enums.UserRole;
import com.example.Project_management_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findFirstByEmail(String username);

    Optional<User> findByUserRole(UserRole userRole);
}
