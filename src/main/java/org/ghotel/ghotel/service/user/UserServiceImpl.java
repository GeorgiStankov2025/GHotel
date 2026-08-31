package org.ghotel.ghotel.service.user;

import org.ghotel.ghotel.dto.response.DeletedDTO;
import org.ghotel.ghotel.dto.response.UserResponseDTO;
import org.ghotel.ghotel.entity.User;
import org.ghotel.ghotel.exception.ResourceNotFoundException;
import org.ghotel.ghotel.mapper.UserMapper;
import org.ghotel.ghotel.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

//    @Transactional
//    @Override
//    public EmployeeResponseDTO addEmployee(EmployeeRequestDTO request) {
//        if (employeeRepository.existsByUsername(request.username())) {
//            throw new InvalidRequestException
//                    ("Employee with username: " + request.username() + " already exists.");
//        }
//        Employee employee = employeeMapper.toEmployeeEntity(request);
//        Employee saved = employeeRepository.save(employee);
//        return employeeMapper.toEmployeeResponseDTO(saved);
//    }

//    @Transactional
//    @Override
//    public EmployeeResponseDTO editEmployee(UUID id, EmployeeRequestDTO request) {
//        Employee employee = findById(id);
//        employee = employeeMapper.updateEmployee(request, employee);
//        return employeeMapper.toEmployeeResponseDTO(employee);
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

//    public List<EmployeeResponseDTO> getDeletedEmployees() {
//        List<Employee> employees = employeeRepository.getAllByDeletedTrue();
//        return employees.stream()
//                .map(employeeMapper::toEmployeeResponseDTO)
//                .toList();
//    }

    @Override
    public User findById(UUID id) {
        return userRepository.getUserByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found with id: " + id));
    }

    @Override
    public User findByIdDeleted(UUID id) {
        return userRepository.getUserByIdAndDeletedTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found with id: " + id));
    }

}
