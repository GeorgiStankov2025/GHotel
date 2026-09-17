package org.ghotel.ghotel.service.user;

import org.ghotel.ghotel.dto.request.UserRequestDTO;
import org.ghotel.ghotel.dto.response.DeletedDTO;
import org.ghotel.ghotel.dto.response.UserResponseDTO;
import org.ghotel.ghotel.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface UserService {
//    UserResponseDTO addUser(EmployeeRequestDTO request);

    UserResponseDTO getUserById(UUID id);

    List<UserResponseDTO> getUsers();

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getDeletedUserById(UUID id);

//    UserResponseDTO editUser(UUID id, UserRequestDTO request);

    @Transactional
    UserResponseDTO addUser(UserRequestDTO request);

    DeletedDTO deleteUser(UUID id);

    UserResponseDTO restoreUser(UUID id);

    User findById(UUID id);

    User findByIdDeleted(UUID id);

    User findByUsername(String username);
}
