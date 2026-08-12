package org.ghotel.ghotel.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.ghotel.ghotel.dto.request.RoomRequestDTO;
import org.ghotel.ghotel.dto.response.RoomReservationsResponseDTO;
import org.ghotel.ghotel.dto.response.RoomResponseDTO;
import org.ghotel.ghotel.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/room")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    @Operation(description = "Add room")
    public ResponseEntity<RoomResponseDTO> addRoom(
            @Valid
            @RequestBody RoomRequestDTO request) {
        RoomResponseDTO response = roomService.addRoom(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(description = "Get a room by id.")
    public ResponseEntity<RoomResponseDTO> getRoomById(
            @PathVariable UUID id) {
        RoomResponseDTO response = roomService.getRoomById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/customer")
    @Operation(description = "Get a room by id with its customer.")
    public ResponseEntity<RoomReservationsResponseDTO> getRoomByIdWithCustomer(
            @PathVariable UUID id) {
        RoomReservationsResponseDTO response = roomService.getRoomWithCustomerById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @Operation(description = "Get all rooms.")
    public ResponseEntity<List<RoomResponseDTO>> getAllRooms() {
        List<RoomResponseDTO> response = roomService.getAllRooms();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/customer")
    @Operation(description = "Get all rooms with their customer.")
    public ResponseEntity<List<RoomReservationsResponseDTO>> getAllRoomsWithCustomer() {
        List<RoomReservationsResponseDTO> response = roomService.getAllRoomsWithCustomer();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(description = "Edit room information.")
    public ResponseEntity<RoomResponseDTO> editRoom(
            @PathVariable UUID id,
            @Valid @RequestBody RoomRequestDTO request) {
        RoomResponseDTO response = roomService.editRoom(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete room.")
    public ResponseEntity<RoomResponseDTO> deleteRoom(
            @PathVariable UUID id) {
        RoomResponseDTO response = roomService.deleteRoom(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{id}/customer/{customerId}/assign")
    @Operation(description = "Take room for customer.")
    public ResponseEntity<RoomReservationsResponseDTO> takeRoom(
            @PathVariable UUID id,
            @PathVariable UUID customerId
    ) {
        RoomReservationsResponseDTO response = roomService.takeRoom(id, customerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{id}/customer/{customerId}/release")
    @Operation(description = "Free room from customer.")
    public ResponseEntity<RoomReservationsResponseDTO> freeRoom(
            @PathVariable UUID id,
            @PathVariable UUID customerId
    ) {
        RoomReservationsResponseDTO response = roomService.freeRoom(id, customerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
