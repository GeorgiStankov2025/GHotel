package org.ghotel.ghotel.api.v1;

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
    public ResponseEntity<RoomResponse> addRoom(
            @Valid
            @RequestBody RoomRequest request) {
        RoomResponse response = roomService.addRoom(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomById(
            @PathVariable Long id) {
        RoomResponse response = roomService.getRoomById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/customer")
    public ResponseEntity<RoomCustomerResponse> getRoomByIdWithCustomer(
            @PathVariable Long id) {
        RoomCustomerResponse response = roomService.getRoomWithCustomerById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        List<RoomResponse> response = roomService.getAllRooms();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/customer")
    public ResponseEntity<List<RoomCustomerResponse>> getAllRoomsWithCustomer() {
        List<RoomCustomerResponse> response = roomService.getAllRoomsWithCustomer();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> editRoom(
            @PathVariable Long id,
            @Valid @RequestBody RoomRequest request) {
        RoomResponse response = roomService.editRoom(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RoomResponse> deleteRoom(
            @PathVariable Long id) {
        RoomResponse response = roomService.deleteRoom(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{id}/customer/assign/{customerId}")
    public ResponseEntity<RoomCustomerResponse> takeRoom(
            @PathVariable Long id,
            @PathVariable Long customerId
    ) {
        RoomCustomerResponse response = roomService.takeRoom(id, customerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{id}/customer/{customerId}/release")
    public ResponseEntity<RoomCustomerResponse> freeRoom(
            @PathVariable Long id,
            @PathVariable Long customerId
    ) {
        RoomCustomerResponse response = roomService.freeRoom(id, customerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
