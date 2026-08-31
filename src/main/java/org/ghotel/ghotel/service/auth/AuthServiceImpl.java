package org.ghotel.ghotel.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.ghotel.ghotel.common.security.service.JwtService;
import org.ghotel.ghotel.dto.request.UserRequestDTO;
import org.ghotel.ghotel.dto.request.LoginRequestDTO;
import org.ghotel.ghotel.dto.request.TokenRequestDTO;
import org.ghotel.ghotel.dto.response.AuthResponseDTO;
import org.ghotel.ghotel.dto.response.UserResponseDTO;
import org.ghotel.ghotel.entity.User;
import org.ghotel.ghotel.exception.BadCredentialsException;
import org.ghotel.ghotel.exception.InvalidRequestException;
import org.ghotel.ghotel.mapper.UserMapper;
import org.ghotel.ghotel.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.getUserByUsernameAndDeletedFalse(request.username())
                .orElseThrow(() -> new InvalidRequestException("Login failed: Employee with username: "
                        + request.username() + " not found."));
        if (!passwordEncoder.matches(request.rawPassword(), user.getPassword())) {
            throw new BadCredentialsException("Login failed: Wrong username or password.");
        }
        log.info("User with username: {} logged in.", request.username());
        return jwtService.generateTokenPair(user.getUsername(), user.getRole().toString());
    }

    @Override
    public UserResponseDTO register(UserRequestDTO request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new InvalidRequestException
                    ("Employee with username: " + request.username() + " already exists.");
        }
        User user = userMapper.toUserEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        User saved = userRepository.save(user);
        log.info("Registered user with username: {}.", request.username());
        return userMapper.toUserResponseDTO(saved);
    }

    @Override
    public AuthResponseDTO refresh(TokenRequestDTO request) {
        return jwtService.refreshAccessToken(request.refreshToken());
    }
}
