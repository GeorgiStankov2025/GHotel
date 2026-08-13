package org.ghotel.ghotel.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.ghotel.ghotel.application.ReservationFacade;
import org.ghotel.ghotel.dto.request.ReservationCustomerRequestDTO;
import org.ghotel.ghotel.dto.request.ReservationRequestDTO;
import org.ghotel.ghotel.dto.request.ReservationRoomRequestDTO;
import org.ghotel.ghotel.dto.response.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationsController {

    private final ReservationFacade reservationFacade;

    public ReservationsController(ReservationFacade reservationFacade) {
        this.reservationFacade = reservationFacade;
    }

    @PostMapping
    @Operation(description = "Add reservation.")
    public ResponseEntity<ReservationResponseDTO> addReservation(
            @Valid
            @RequestBody ReservationRequestDTO request) {
        ReservationResponseDTO response = reservationFacade.addReservation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(description = "Get reservation by id.")
    public ResponseEntity<ReservationResponseDTO> getReservationById(
            @PathVariable UUID id) {
        ReservationResponseDTO response = reservationFacade.getReservationById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/customer")
    @Operation(description = "Get reservation by id with customer.")
    public ResponseEntity<ReservationCustomerResponseDTO> getReservationWithCustomerById(
            @PathVariable UUID id) {
        ReservationCustomerResponseDTO response = reservationFacade.getReservationWithCustomerById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/rooms")
    @Operation(description = "Get reservation by id with rooms.")
    public ResponseEntity<ReservationRoomsResponseDTO> getReservationWithRoomsById(
            @PathVariable UUID id) {
        ReservationRoomsResponseDTO response = reservationFacade.getReservationWithRoomsById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/details")
    @Operation(description = "Get reservation by id with rooms and customer.")
    public ResponseEntity<ReservationRoomsCustomerResponseDTO> getReservationWithRoomsAndCustomerById(
            @PathVariable UUID id) {
        ReservationRoomsCustomerResponseDTO response = reservationFacade.getReservationWithRoomsAndCustomerById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @Operation(description = "Get all reservations.")
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservations() {
        List<ReservationResponseDTO> response = reservationFacade.getAllReservations();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/customer")
    @Operation(description = "Get all reservations with customer.")
    public ResponseEntity<List<ReservationCustomerResponseDTO>> getAllReservationsWithCustomer() {
        List<ReservationCustomerResponseDTO> response = reservationFacade.getAllReservationsWithCustomer();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/rooms")
    @Operation(description = "Get all reservations with rooms.")
    public ResponseEntity<List<ReservationRoomsResponseDTO>> getAllReservationsWithRooms() {
        List<ReservationRoomsResponseDTO> response = reservationFacade.getAllReservationsWithRooms();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/details")
    @Operation(description = "Get all reservations with rooms.")
    public ResponseEntity<List<ReservationRoomsCustomerResponseDTO>> getAllReservationsWithRoomsAndCustomer() {
        List<ReservationRoomsCustomerResponseDTO> response = reservationFacade.getAllReservationsWithRoomsAndCustomer();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(description = "Edit reservation information.")
    public ResponseEntity<ReservationResponseDTO> editReservation(
            @PathVariable UUID id,
            @Valid @RequestBody ReservationRequestDTO request) {
        ReservationResponseDTO response = reservationFacade.editReservation(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete reservation.")
    public ResponseEntity<DeletedDTO> deleteReservation(
            @PathVariable UUID id) {
        DeletedDTO response = reservationFacade.deleteReservation(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/rooms")
    @Operation(description = "Add room to reservation")
    public ResponseEntity<ReservationRoomsResponseDTO> addRoomToReservation(
            @Valid @RequestBody ReservationRoomRequestDTO request) {
        ReservationRoomsResponseDTO response = reservationFacade.addRoomToReservation(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/rooms")
    @Operation(description = "Remove room from reservation")
    public ResponseEntity<ReservationRoomsResponseDTO> removeRoomFromReservation(
            @Valid @RequestBody ReservationRoomRequestDTO request) {
        ReservationRoomsResponseDTO response = reservationFacade.removeRoomFromReservation(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/customer")
    @Operation(description = "Set reservation customer")
    public ResponseEntity<ReservationCustomerResponseDTO> setReservationCustomer(
            @Valid @RequestBody ReservationCustomerRequestDTO request) {
        ReservationCustomerResponseDTO response = reservationFacade.setReservationCustomer(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
