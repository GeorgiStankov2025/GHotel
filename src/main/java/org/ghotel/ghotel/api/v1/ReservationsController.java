package org.ghotel.ghotel.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.ghotel.ghotel.application.ReservationFacade;
import org.ghotel.ghotel.dto.request.ReservationCustomerRequestDTO;
import org.ghotel.ghotel.dto.request.ReservationRequestDTO;
import org.ghotel.ghotel.dto.request.ReservationRoomRequestDTO;
import org.ghotel.ghotel.dto.response.*;
import org.ghotel.ghotel.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationsController {

    private final ReservationFacade reservationFacade;
    private final ReservationService reservationService;

    public ReservationsController(ReservationFacade reservationFacade, ReservationService reservationService) {
        this.reservationFacade = reservationFacade;
        this.reservationService = reservationService;
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
        ReservationResponseDTO response = reservationService.getReservationById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/customer")
    @Operation(description = "Get reservation by id with customer.")
    public ResponseEntity<ReservationCustomerResponseDTO> getReservationWithCustomerById(
            @PathVariable UUID id) {
        ReservationCustomerResponseDTO response = reservationService.getReservationWithCustomerById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/rooms")
    @Operation(description = "Get reservation by id with rooms.")
    public ResponseEntity<ReservationRoomsResponseDTO> getReservationWithRoomsById(
            @PathVariable UUID id) {
        ReservationRoomsResponseDTO response = reservationService.getReservationWithRoomsById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/details")
    @Operation(description = "Get reservation by id with rooms and customer.")
    public ResponseEntity<ReservationRoomsCustomerResponseDTO> getReservationWithRoomsAndCustomerById(
            @PathVariable UUID id) {
        ReservationRoomsCustomerResponseDTO response = reservationService.getReservationWithRoomsAndCustomerById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @Operation(description = "Get all reservations.")
    public ResponseEntity<List<ReservationResponseDTO>> getReservations() {
        List<ReservationResponseDTO> response = reservationService.getReservations();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/customer")
    @Operation(description = "Get all reservations with customer.")
    public ResponseEntity<List<ReservationCustomerResponseDTO>> getReservationsWithCustomer() {
        List<ReservationCustomerResponseDTO> response =
                reservationService.getReservationsWithCustomer();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/rooms")
    @Operation(description = "Get all reservations with rooms.")
    public ResponseEntity<List<ReservationRoomsResponseDTO>> getReservationsWithRooms() {
        List<ReservationRoomsResponseDTO> response =
                reservationService.getReservationsWithRooms();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/details")
    @Operation(description = "Get all reservations with rooms and customer.")
    public ResponseEntity<List<ReservationRoomsCustomerResponseDTO>> getReservationsWithRoomsAndCustomer() {
        List<ReservationRoomsCustomerResponseDTO> response =
                reservationService.getReservationsWithRoomsAndCustomer();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/deleted/{id}")
    @Operation(description = "Get reservation by id.")
    public ResponseEntity<ReservationResponseDTO> getDeletedReservationById(
            @PathVariable UUID id) {
        ReservationResponseDTO response = reservationService.getDeletedReservationById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/deleted/{id}/customer")
    @Operation(description = "Get reservation by id with customer.")
    public ResponseEntity<ReservationCustomerResponseDTO> getDeletedReservationWithCustomerById(
            @PathVariable UUID id) {
        ReservationCustomerResponseDTO response = reservationService.getDeletedReservationWithCustomerById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/deleted/{id}/rooms")
    @Operation(description = "Get reservation by id with rooms.")
    public ResponseEntity<ReservationRoomsResponseDTO> getDeletedReservationWithRoomsById(
            @PathVariable UUID id) {
        ReservationRoomsResponseDTO response = reservationService.getDeletedReservationWithRoomsById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/deleted/{id}/details")
    @Operation(description = "Get reservation by id with rooms and customer.")
    public ResponseEntity<ReservationRoomsCustomerResponseDTO> getDeletedReservationWithRoomsAndCustomerById(
            @PathVariable UUID id) {
        ReservationRoomsCustomerResponseDTO response = reservationService.getDeletedReservationWithRoomsAndCustomerById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/deleted")
    @Operation(description = "Get all reservations.")
    public ResponseEntity<List<ReservationResponseDTO>> getDeletedReservations() {
        List<ReservationResponseDTO> response = reservationService.getDeletedReservations();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/deleted/customer")
    @Operation(description = "Get all reservations with customer.")
    public ResponseEntity<List<ReservationCustomerResponseDTO>> getDeletedReservationsWithCustomer() {
        List<ReservationCustomerResponseDTO> response =
                reservationService.getDeletedReservationsWithCustomer();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/deleted/rooms")
    @Operation(description = "Get all reservations with rooms.")
    public ResponseEntity<List<ReservationRoomsResponseDTO>> getDeletedReservationsWithRooms() {
        List<ReservationRoomsResponseDTO> response =
                reservationService.getDeletedReservationsWithRooms();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/deleted/details")
    @Operation(description = "Get all reservations with rooms and customer.")
    public ResponseEntity<List<ReservationRoomsCustomerResponseDTO>> getDeletedReservationsWithRoomsAndCustomer() {
        List<ReservationRoomsCustomerResponseDTO> response =
                reservationService.getDeletedReservationsWithRoomsAndCustomer();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/all")
    @Operation(description = "Get all reservations including the soft deleted ones.")
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservations() {
        List<ReservationResponseDTO> response = reservationService.getAllReservations();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all/customer")
    @Operation(description = "Get all reservations with customer including the soft deleted ones.")
    public ResponseEntity<List<ReservationCustomerResponseDTO>> getAllReservationsWithCustomer() {
        List<ReservationCustomerResponseDTO> response =
                reservationService.getAllReservationsWithCustomer();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all/rooms")
    @Operation(description = "Get all reservations with rooms including the soft deleted ones.")
    public ResponseEntity<List<ReservationRoomsResponseDTO>> getAllReservationsWithRooms() {
        List<ReservationRoomsResponseDTO> response =
                reservationService.getAllReservationsWithRooms();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all/details")
    @Operation(description = "Get all reservations with rooms and customer including the soft deleted ones.")
    public ResponseEntity<List<ReservationRoomsCustomerResponseDTO>> getAllReservationsWithRoomsAndCustomer() {
        List<ReservationRoomsCustomerResponseDTO> response =
                reservationService.getAllReservationsWithRoomsAndCustomer();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(description = "Edit reservation information.")
    public ResponseEntity<ReservationResponseDTO> editReservation(
            @PathVariable UUID id,
            @Valid @RequestBody ReservationRequestDTO request) {
        ReservationResponseDTO response = reservationService.editReservation(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete reservation.")
    public ResponseEntity<DeletedDTO> deleteReservation(
            @PathVariable UUID id) {
        DeletedDTO response = reservationService.deleteReservation(id);
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
