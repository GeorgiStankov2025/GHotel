package org.ghotel.ghotel.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.ghotel.ghotel.common.security.service.JwtService;
import org.ghotel.ghotel.dto.request.LoginRequestDTO;
import org.ghotel.ghotel.dto.request.TokenRequestDTO;
import org.ghotel.ghotel.dto.request.UserRequestDTO;
import org.ghotel.ghotel.dto.response.AuthResponseDTO;
import org.ghotel.ghotel.dto.response.UserResponseDTO;
import org.ghotel.ghotel.entity.User;
import org.ghotel.ghotel.exception.BadCredentialsException;
import org.ghotel.ghotel.service.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        User user = userService.findByUsername(request.username());
        if (!passwordEncoder.matches(request.rawPassword(), user.getPassword())) {
            throw new BadCredentialsException("Login failed: Wrong username or password.");
        }
        log.info("User with username: {} logged in.", request.username());
        return jwtService.generateTokenPair(user.getUsername(), user.getRole().toString());
    }

    @Override
    public UserResponseDTO register(UserRequestDTO request) {
        log.info("Registered user with username: {}.", request.username());
        return userService.addUser(request);
    }

    @Override
    public AuthResponseDTO refresh(TokenRequestDTO request) {
        return jwtService.refreshAccessToken(request.refreshToken());
    }
}
