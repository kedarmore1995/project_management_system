package com.example.Project_management_system.controller.auth;

import com.example.Project_management_system.dto.AuthenticationRequest;
import com.example.Project_management_system.dto.AuthenticationResponse;
import com.example.Project_management_system.dto.SignupRequest;
import com.example.Project_management_system.dto.UserDto;
import com.example.Project_management_system.model.User;
import com.example.Project_management_system.repo.UserRepository;
import com.example.Project_management_system.services.auth.AuthService;
import com.example.Project_management_system.services.jwt.UserService;
import com.example.Project_management_system.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Signature;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    private final UserRepository userRepository;

    private final UserService userService;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        if(authService.hasUserWithEmail(signupRequest.getEmail()))
            return  ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exists with this email");
        UserDto createdUserDto = authService.signupUser(signupRequest);
        if(createdUserDto == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password");
        };
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail(authenticationRequest.getEmail());
        final String jwtToken = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwtToken);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }
        return authenticationResponse;
    }
}
