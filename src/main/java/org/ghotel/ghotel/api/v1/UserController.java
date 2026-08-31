package org.ghotel.ghotel.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.ghotel.ghotel.dto.response.DeletedDTO;
import org.ghotel.ghotel.dto.response.UserResponseDTO;
import org.ghotel.ghotel.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping
//    @Operation(description = "Add User.")
//    public ResponseEntity<UserResponseDTO> addUser(
//            @Valid
//            @RequestBody UserRequestDTO request) {
//        UserResponseDTO response = UserService.addUser(request);
//        log.info("Created User with id: {}", response.id());
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }

    @GetMapping("/{id}")
    @Operation(description = "Get User by id.")
    public ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable UUID id) {
        UserResponseDTO response = userService.getUserById(id);
        log.info("Found User with id: {}", response.id());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @Operation(description = "Get all Users.")
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        List<UserResponseDTO> response = userService.getUsers();
        log.info("Found all undeleted Users");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/deleted/{id}")
    @Operation(description = "Get deleted User by id.")
    public ResponseEntity<UserResponseDTO> getDeletedUserById(
            @PathVariable UUID id) {
        UserResponseDTO response = userService.getDeletedUserById(id);
        log.info("Found deleted User with id: {}", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @GetMapping("/deleted")
//    @Operation(description = "Get all deleted Users.")
//    public ResponseEntity<List<UserResponseDTO>> getDeletedUsers() {
//        List<UserResponseDTO> response = UserService.getDeletedUsers();
//        log.info("Found all deleted Users.");
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @GetMapping("/all")
    @Operation(description = "Get all Users including soft deleted ones.")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> response = userService.getAllUsers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @PutMapping("/{id}")
//    @Operation(description = "Edit User information.")
//    public ResponseEntity<UserResponseDTO> editUser(
//            @PathVariable UUID id,
//            @Valid @RequestBody UserRequestDTO request) {
//        UserResponseDTO response = UserService.editUser(id, request);
//        log.info("Modified User with id: {} ", id);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete User.")
    public ResponseEntity<DeletedDTO> deleteUser(
            @PathVariable UUID id) {
        DeletedDTO response = userService.deleteUser(id);
        log.info("Deleted User with id: {} ", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}/restore")
    @Operation(description = "Restore User.")
    public ResponseEntity<UserResponseDTO> restoreUser(
            @PathVariable UUID id) {
        UserResponseDTO response = userService.restoreUser(id);
        log.info("Restored User with id: {} ", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
