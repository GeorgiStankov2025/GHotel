package org.ghotel.ghotel.service.user;

import org.ghotel.ghotel.dto.request.UserRequestDTO;
import org.ghotel.ghotel.dto.response.DeletedDTO;
import org.ghotel.ghotel.dto.response.UserResponseDTO;
import org.ghotel.ghotel.entity.User;
import org.ghotel.ghotel.exception.InvalidRequestException;
import org.ghotel.ghotel.exception.ResourceNotFoundException;
import org.ghotel.ghotel.mapper.UserMapper;
import org.ghotel.ghotel.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserResponseDTO addUser(UserRequestDTO request) {
        if (existsByUsername(request.username())) {
            throw new InvalidRequestException
                    ("User with username: " + request.username() + " already exists.");
        }
        User user = userMapper.toUserEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        User saved = userRepository.save(user);
        return userMapper.toUserResponseDTO(saved);
    }

//    @Transactional
//    @Override
//    public UserResponseDTO editUser(UUID id, UserRequestDTO request) {
//        User user = findById(id);
//        user = userMapper.updateUser(request, user);
//        return userMapper.toUserResponseDTO(user);
//    }

    @Transactional
    @Override
    public DeletedDTO deleteUser(UUID id) {
        User user = findById(id);
        user.setDeleted(true);
        return new DeletedDTO("Resource deleted successfully.");
    }

    @Transactional
    @Override
    public UserResponseDTO restoreUser(UUID id) {
        User user = findByIdDeleted(id);
        user.setDeleted(false);
        return userMapper.toUserResponseDTO(user);
    }

    @Override
    public UserResponseDTO getUserById(UUID id) {
        User user = findById(id);
        return userMapper.toUserResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> getUsers() {
        List<User> users = userRepository.getAllByDeletedFalse();
        return users.stream()
                .map(userMapper::toUserResponseDTO)
                .toList();
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserResponseDTO)
                .toList();
    }

    @Override
    public UserResponseDTO getDeletedUserById(UUID id) {
        User user = findByIdDeleted(id);
        return userMapper.toUserResponseDTO(user);
    }

//    public List<UserResponseDTO> getDeletedUsers() {
//        List<User> users = userRepository.getAllByDeletedTrue();
//        return users.stream()
//                .map(userMapper::toUserResponseDTO)
//                .toList();
//    }

    @Override
    public User findById(UUID id) {
        return userRepository.getUserByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public User findByIdDeleted(UUID id) {
        return userRepository.getUserByIdAndDeletedTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.getUserByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with username: " + username));
    }

    private boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

}
