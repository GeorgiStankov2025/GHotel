package org.ghotel.ghotel.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.ghotel.ghotel.dto.request.RoomRequest;
import org.ghotel.ghotel.dto.response.RoomCustomerResponse;
import org.ghotel.ghotel.dto.response.RoomResponse;
import org.ghotel.ghotel.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    @Operation(description = "Add room")
    public ResponseEntity<RoomResponse> addRoom(
            @Valid
            @RequestBody RoomRequest request) {
        RoomResponse response = roomService.addRoom(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(description = "Get a room by id.")
    public ResponseEntity<RoomResponse> getRoomById(
            @PathVariable Long id) {
        RoomResponse response = roomService.getRoomById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/customer")
    @Operation(description = "Get a room by id with its customer.")
    public ResponseEntity<RoomCustomerResponse> getRoomByIdWithCustomer(
            @PathVariable Long id) {
        RoomCustomerResponse response = roomService.getRoomWithCustomerById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @Operation(description = "Get all rooms.")
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        List<RoomResponse> response = roomService.getAllRooms();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/customer")
    @Operation(description = "Get all rooms with their customer.")
    public ResponseEntity<List<RoomCustomerResponse>> getAllRoomsWithCustomer() {
        List<RoomCustomerResponse> response = roomService.getAllRoomsWithCustomer();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(description = "Edit room information.")
    public ResponseEntity<RoomResponse> editRoom(
            @PathVariable Long id,
            @Valid @RequestBody RoomRequest request) {
        RoomResponse response = roomService.editRoom(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete room.")
    public ResponseEntity<RoomResponse> deleteRoom(
            @PathVariable Long id) {
        RoomResponse response = roomService.deleteRoom(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{id}/customer/assign/{customerId}")
    @Operation(description = "Take room for customer.")
    public ResponseEntity<RoomCustomerResponse> takeRoom(
            @PathVariable Long id,
            @PathVariable Long customerId
    ) {
        RoomCustomerResponse response = roomService.takeRoom(id, customerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{id}/customer/{customerId}/release")
    @Operation(description = "Free room from customer.")
    public ResponseEntity<RoomCustomerResponse> freeRoom(
            @PathVariable Long id,
            @PathVariable Long customerId
    ) {
        RoomCustomerResponse response = roomService.freeRoom(id, customerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
