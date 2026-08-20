package org.ghotel.ghotel.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.ghotel.ghotel.application.ReservationFacade;
import org.ghotel.ghotel.dto.request.ReservationCustomerRequestDTO;
import org.ghotel.ghotel.dto.request.ReservationRequestDTO;
import org.ghotel.ghotel.dto.request.ReservationRoomRequestDTO;
import org.ghotel.ghotel.dto.response.*;
import org.ghotel.ghotel.service.reservation.IReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationsController {

    private final ReservationFacade reservationFacade;
    private final IReservationService reservationService;

    public ReservationsController(ReservationFacade reservationFacade, IReservationService reservationService) {
        this.reservationFacade = reservationFacade;
        this.reservationService = reservationService;
    }

    @PostMapping
    @Operation(description = "Add reservation.")
    public ResponseEntity<ReservationResponseDTO> addReservation(
            @Valid
            @RequestBody ReservationRequestDTO request) {
        ReservationResponseDTO response = reservationFacade.addReservation(request);
        log.info("Created reservation with id: {}", response.id());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(description = "Get reservation by id.")
    public ResponseEntity<ReservationResponseDTO> getReservationById(
            @PathVariable UUID id) {
        ReservationResponseDTO response = reservationService.getReservationById(id);
        log.info("Found reservation with id: {}", response.id());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @GetMapping("/{id}/customer")
//    @Operation(description = "Get reservation by id with customer.")
//    public ResponseEntity<ReservationCustomerResponseDTO> getReservationWithCustomerById(
//            @PathVariable UUID id) {
//        ReservationCustomerResponseDTO response = reservationService.getReservationWithCustomerById(id);
//        log.info("Found reservation with id: {} and its customer", response.reservationId());
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}/rooms")
//    @Operation(description = "Get reservation by id with rooms.")
//    public ResponseEntity<ReservationRoomsResponseDTO> getReservationWithRoomsById(
//            @PathVariable UUID id) {
//        ReservationRoomsResponseDTO response = reservationService.getReservationWithRoomsById(id);
//        log.info("Found reservation with id: {} and its rooms.", response.reservationId());
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @GetMapping("/{id}/details")
    @Operation(description = "Get reservation by id with rooms and customer.")
    public ResponseEntity<ReservationRoomsCustomerResponseDTO> getReservationWithRoomsAndCustomerById(
            @PathVariable UUID id) {
        ReservationRoomsCustomerResponseDTO response = reservationService
                .getReservationWithRoomsAndCustomerById(id);
        log.info("Found reservation with id: {} and its rooms and customer.", response.reservationId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @Operation(description = "Get all reservations.")
    public ResponseEntity<List<ReservationResponseDTO>> getReservations() {
        List<ReservationResponseDTO> response = reservationService.getReservations();
        log.info("Found all undeleted reservations");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @GetMapping("/customer")
//    @Operation(description = "Get all reservations with customer.")
//    public ResponseEntity<List<ReservationCustomerResponseDTO>> getReservationsWithCustomer() {
//        List<ReservationCustomerResponseDTO> response =
//                reservationService.getReservationsWithCustomer();
//        log.info("Found all undeleted reservations with customer");
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @GetMapping("/rooms")
//    @Operation(description = "Get all reservations with rooms.")
//    public ResponseEntity<List<ReservationRoomsResponseDTO>> getReservationsWithRooms() {
//        List<ReservationRoomsResponseDTO> response =
//                reservationService.getReservationsWithRooms();
//        log.info("Found all undeleted reservations with rooms");
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @GetMapping("/details")
    @Operation(description = "Get all reservations with rooms and customer.")
    public ResponseEntity<List<ReservationRoomsCustomerResponseDTO>> getReservationsWithRoomsAndCustomer() {
        List<ReservationRoomsCustomerResponseDTO> response =
                reservationService.getReservationsWithRoomsAndCustomer();
        log.info("Found all undeleted reservations with rooms and customer.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/deleted/{id}")
    @Operation(description = "Get reservation by id.")
    public ResponseEntity<ReservationResponseDTO> getDeletedReservationById(
            @PathVariable UUID id) {
        ReservationResponseDTO response = reservationService.getDeletedReservationById(id);
        log.info("Found deleted reservation with id: {}", response.id());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @GetMapping("/deleted/{id}/customer")
//    @Operation(description = "Get reservation by id with customer.")
//    public ResponseEntity<ReservationCustomerResponseDTO> getDeletedReservationWithCustomerById(
//            @PathVariable UUID id) {
//        ReservationCustomerResponseDTO response = reservationService.getDeletedReservationWithCustomerById(id);
//        log.info("Found deleted reservation with id: {} and customer", response.reservationId());
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @GetMapping("/deleted/{id}/rooms")
//    @Operation(description = "Get reservation by id with rooms.")
//    public ResponseEntity<ReservationRoomsResponseDTO> getDeletedReservationWithRoomsById(
//            @PathVariable UUID id) {
//        ReservationRoomsResponseDTO response = reservationService.getDeletedReservationWithRoomsById(id);
//        log.info("Found deleted reservation with id: {} and rooms", response.reservationId());
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @GetMapping("/deleted/{id}/details")
//    @Operation(description = "Get reservation by id with rooms and customer.")
//    public ResponseEntity<ReservationRoomsCustomerResponseDTO> getDeletedReservationWithRoomsAndCustomerById(
//            @PathVariable UUID id) {
//        ReservationRoomsCustomerResponseDTO response =
//                reservationService.getDeletedReservationWithRoomsAndCustomerById(id);
//        log.info("Found deleted reservation with id: {} and customer+rooms", response.reservationId());
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    //    @GetMapping("/deleted")
//    @Operation(description = "Get all reservations.")
//    public ResponseEntity<List<ReservationResponseDTO>> getDeletedReservations() {
//        List<ReservationResponseDTO> response = reservationService.getDeletedReservations();
//        log.info("Found all deleted reservations");
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @GetMapping("/deleted/customer")
//    @Operation(description = "Get all reservations with customer.")
//    public ResponseEntity<List<ReservationCustomerResponseDTO>> getDeletedReservationsWithCustomer() {
//        List<ReservationCustomerResponseDTO> response =
//                reservationService.getDeletedReservationsWithCustomer();
//        log.info("Found all deleted reservations with their customer");
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @GetMapping("/deleted/rooms")
//    @Operation(description = "Get all reservations with rooms.")
//    public ResponseEntity<List<ReservationRoomsResponseDTO>> getDeletedReservationsWithRooms() {
//        List<ReservationRoomsResponseDTO> response =
//                reservationService.getDeletedReservationsWithRooms();
//        log.info("Found all deleted reservations with their rooms");
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @GetMapping("/deleted/details")
//    @Operation(description = "Get all reservations with rooms and customer.")
//    public ResponseEntity<List<ReservationRoomsCustomerResponseDTO>> getDeletedReservationsWithRoomsAndCustomer() {
//        List<ReservationRoomsCustomerResponseDTO> response =
//                reservationService.getDeletedReservationsWithRoomsAndCustomer();
//        log.info("Found all deleted reservations with their rooms and customer");
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
    @GetMapping("/all")
    @Operation(description = "Get all reservations including the soft deleted ones.")
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservations() {
        List<ReservationResponseDTO> response = reservationService.getAllReservations();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @GetMapping("/all/customer")
//    @Operation(description = "Get all reservations with customer including the soft deleted ones.")
//    public ResponseEntity<List<ReservationCustomerResponseDTO>> getAllReservationsWithCustomer() {
//        List<ReservationCustomerResponseDTO> response =
//                reservationService.getAllReservationsWithCustomer();
//        log.info("Found all reservations");
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @GetMapping("/all/rooms")
//    @Operation(description = "Get all reservations with rooms including the soft deleted ones.")
//    public ResponseEntity<List<ReservationRoomsResponseDTO>> getAllReservationsWithRooms() {
//        List<ReservationRoomsResponseDTO> response =
//                reservationService.getAllReservationsWithRooms();
//        log.info("Found all reservations with rooms");
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @GetMapping("/all/details")
    @Operation(description = "Get all reservations with rooms and customer including the soft deleted ones.")
    public ResponseEntity<List<ReservationRoomsCustomerResponseDTO>> getAllReservationsWithRoomsAndCustomer() {
        List<ReservationRoomsCustomerResponseDTO> response =
                reservationService.getAllReservationsWithRoomsAndCustomer();
        log.info("Found all reservations with rooms and customer");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(description = "Edit reservation information.")
    public ResponseEntity<ReservationResponseDTO> editReservation(
            @PathVariable UUID id,
            @Valid @RequestBody ReservationRequestDTO request) {
        ReservationResponseDTO response = reservationService.editReservation(id, request);
        log.info("Modified reservation with id: {} ", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete reservation.")
    public ResponseEntity<DeletedDTO> deleteReservation(
            @PathVariable UUID id) {
        DeletedDTO response = reservationService.deleteReservation(id);
        log.info("Deleted reservation with id: {} ", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}/restore")
    @Operation(description = "Restore reservation.")
    public ResponseEntity<ReservationResponseDTO> restoreReservation(
            @PathVariable UUID id) {
        ReservationResponseDTO response = reservationService.restoreReservation(id);
        log.info("Restored reservation with id: {} ", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/rooms")
    @Operation(description = "Add room to reservation")
    public ResponseEntity<ReservationRoomsResponseDTO> addRoomToReservation(
            @Valid @RequestBody ReservationRoomRequestDTO request) {
        ReservationRoomsResponseDTO response = reservationFacade.addRoomToReservation(request);
        log.info("Added room with id: {} to reservation with id: {} ", request.roomId(), response.reservationId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/rooms")
    @Operation(description = "Remove room from reservation")
    public ResponseEntity<ReservationRoomsResponseDTO> removeRoomFromReservation(
            @Valid @RequestBody ReservationRoomRequestDTO request) {
        ReservationRoomsResponseDTO response = reservationFacade.removeRoomFromReservation(request);
        log.info("Removed room with id: {} from reservation with id: {} ", request.roomId(), response.reservationId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/customer")
    @Operation(description = "Set reservation customer")
    public ResponseEntity<ReservationCustomerResponseDTO> setReservationCustomer(
            @Valid @RequestBody ReservationCustomerRequestDTO request) {
        ReservationCustomerResponseDTO response = reservationFacade.setReservationCustomer(request);
        log.info("Set customer with id: {} to reservation with id: {} ", request.customerId(), response.reservationId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
