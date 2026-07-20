package com.metesevim.staj2026.service;

import com.metesevim.staj2026.dto.auth.AuthResponse;
import com.metesevim.staj2026.dto.auth.LoginRequest;
import com.metesevim.staj2026.dto.auth.RegisterRequest;
import com.metesevim.staj2026.entity.AppUser;
import com.metesevim.staj2026.entity.Role;
import com.metesevim.staj2026.exception.InvalidCredentialsException;
import com.metesevim.staj2026.exception.UsernameAlreadyExistsException;
import com.metesevim.staj2026.repository.UserRepository;
import com.metesevim.staj2026.security.CustomUserDetailsService;
import com.metesevim.staj2026.security.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            CustomUserDetailsService userDetailsService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.username())) {
            throw new UsernameAlreadyExistsException(
                    "Username is already taken: " + request.username()
            );
        }

        AppUser user = new AppUser();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);

        AppUser savedUser = userRepository.save(user);

        return new AuthResponse(
                "User registered successfully",
                savedUser.getUsername(),
                savedUser.getRole().name()
        );
    }

    public AuthResponse login(LoginRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()
                    )
            );
        } catch (BadCredentialsException exception) {
            throw new InvalidCredentialsException(
                    "Invalid username or password"
            );
        }

        AppUser user = userRepository
                .findByUsername(request.username())
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid username or password"
                        )
                );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(user.getUsername());

        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(
                token,
                user.getUsername(),
                user.getRole().name()
        );
    }
}