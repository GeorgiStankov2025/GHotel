package org.ghotel.ghotel.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.ghotel.ghotel.dto.request.RoomRequestDTO;
import org.ghotel.ghotel.dto.response.DeletedDTO;
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

    @GetMapping("/{id}/reservations")
    @Operation(description = "Get a room by id with its reservations.")
    public ResponseEntity<RoomReservationsResponseDTO> getRoomByIdWithReservations(
            @PathVariable UUID id) {
        RoomReservationsResponseDTO response = roomService.getRoomWithReservationById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @Operation(description = "Get all rooms.")
    public ResponseEntity<List<RoomResponseDTO>> getRooms() {
        List<RoomResponseDTO> response = roomService.getRooms();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/reservations")
    @Operation(description = "Get all rooms with their reservations.")
    public ResponseEntity<List<RoomReservationsResponseDTO>> getRoomsWithReservations() {
        List<RoomReservationsResponseDTO> response = roomService.getRoomsWithReservations();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/deleted/{id}")
    @Operation(description = "Get a deleted room by id.")
    public ResponseEntity<RoomResponseDTO> getDeletedRoomById(
            @PathVariable UUID id) {
        RoomResponseDTO response = roomService.getDeletedRoomById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/deleted/{id}/reservations")
    @Operation(description = "Get a deleted room by id with its reservations.")
    public ResponseEntity<RoomReservationsResponseDTO> getDeletedRoomByIdWithReservations(
            @PathVariable UUID id) {
        RoomReservationsResponseDTO response = roomService.getDeletedRoomWithReservationById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/deleted")
    @Operation(description = "Get all deleted rooms.")
    public ResponseEntity<List<RoomResponseDTO>> getDeletedRooms() {
        List<RoomResponseDTO> response = roomService.getDeletedRooms();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/deleted/reservations")
    @Operation(description = "Get all deleted rooms with their reservations.")
    public ResponseEntity<List<RoomReservationsResponseDTO>> getDeletedRoomsWithReservations() {
        List<RoomReservationsResponseDTO> response = roomService.getDeletedRoomsWithReservations();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    @Operation(description = "Get all rooms including soft deleted ones.")
    public ResponseEntity<List<RoomResponseDTO>> getAllRooms() {
        List<RoomResponseDTO> response = roomService.getAllRooms();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all/reservations")
    @Operation(description = "Get all rooms with their reservations including soft deleted ones.")
    public ResponseEntity<List<RoomReservationsResponseDTO>> getAllRoomsWithReservations() {
        List<RoomReservationsResponseDTO> response = roomService.getAllRoomsWithReservations();
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
    public ResponseEntity<DeletedDTO> deleteRoom(
            @PathVariable UUID id) {
        DeletedDTO response = roomService.deleteRoom(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}/restore")
    @Operation(description = "Restore room.")
    public ResponseEntity<RoomResponseDTO> restoreRoom(
            @PathVariable UUID id) {
        RoomResponseDTO response = roomService.restoreRoom(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
