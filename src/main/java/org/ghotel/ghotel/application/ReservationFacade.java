package org.ghotel.ghotel.application;

import org.ghotel.ghotel.dto.request.ReservationCustomerRequestDTO;
import org.ghotel.ghotel.dto.request.ReservationRequestDTO;
import org.ghotel.ghotel.dto.request.ReservationRoomRequestDTO;
import org.ghotel.ghotel.dto.response.*;
import org.ghotel.ghotel.entity.Customer;
import org.ghotel.ghotel.entity.Reservation;
import org.ghotel.ghotel.entity.Room;
import org.ghotel.ghotel.exception.InvalidRequestException;
import org.ghotel.ghotel.mapper.ReservationMapper;
import org.ghotel.ghotel.service.CustomerService;
import org.ghotel.ghotel.service.ReservationService;
import org.ghotel.ghotel.service.RoomService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@Transactional(readOnly = true)
public class ReservationFacade {

    private final ReservationService reservationService;
    private final CustomerService customerService;
    private final RoomService roomService;
    private final ReservationMapper reservationMapper;

    public ReservationFacade(ReservationService reservationService, CustomerService customerService, RoomService roomService, ReservationMapper reservationMapper) {
        this.reservationService = reservationService;
        this.customerService = customerService;
        this.roomService = roomService;
        this.reservationMapper = reservationMapper;
    }

    @Transactional
    public ReservationRoomsResponseDTO addRoomToReservation(ReservationRoomRequestDTO request) {

        Reservation reservation = reservationService.findWithRoomsByIdUndeleted(request.reservationId());
        Room room = roomService.findRoomById(request.roomId());
        if (reservation.containsRoom(room)) {
            throw new InvalidRequestException("Cannot add room to reservation.");
        }
        reservation.addRoom(room);
        return reservationMapper.toReservationRoomsResponseDTO(reservation);
    }

    @Transactional
    public ReservationRoomsResponseDTO removeRoomFromReservation(ReservationRoomRequestDTO request) {
        Reservation reservation = reservationService.findWithRoomsByIdUndeleted(request.reservationId());
        Room room = roomService.findRoomById(request.roomId());
        if (!reservation.containsRoom(room)) {
            throw new InvalidRequestException("Room not in reservation.");
        }
        reservation.removeRoom(room);
        return reservationMapper.toReservationRoomsResponseDTO(reservation);
    }

    //ToDO: Make sure no customer is randomly removed/replaced.
    @Transactional
    public ReservationCustomerResponseDTO setReservationCustomer(ReservationCustomerRequestDTO request) {
        Reservation reservation = reservationService.findWithCustomerByIdUndeleted(request.reservationId());
        Customer customer = customerService.findCustomerById(request.customerId());
        if (reservation.hasCustomer(customer)) {
            throw new InvalidRequestException("Cannot add customer");
        }
        reservation.setCustomer(customer);
        return reservationMapper.toReservationCustomerResponseDTO(reservation);
    }

//    @Transactional
//    public ReservationCustomerResponseDTO removeCustomerFromReservation(UUID reservationId, UUID customerId) {
//        Reservation reservation = reservationService.findWithCustomerByIdUndeleted(reservationId);
//        Customer customer = customerService.findCustomerById(customerId);
//        if (!reservation.hasCustomer(customer)) {
//            throw new InvalidRequestException("Customer not in reservation.");
//        }
//        reservation.unsetCustomer(customer);
//        return reservationMapper.toReservationCustomerResponseDTO(reservation);
//    }

    @Transactional
    public ReservationResponseDTO addReservation(ReservationRequestDTO request) {
        Customer customer = customerService.findCustomerById(request.customerId());
        return reservationService.addReservation(request, customer);
    }


    @Transactional
    public ReservationResponseDTO editReservation(UUID id, ReservationRequestDTO request) {
        return reservationService.editReservation(id, request);
    }

    @Transactional
    public DeletedDTO deleteReservation(UUID id) {
        return reservationService.deleteReservation(id);
    }

    //Reservation fields only
    public ReservationResponseDTO getReservationById(UUID id) {
        return reservationService.getReservationById(id);
    }

    public List<ReservationResponseDTO> getAllReservations() {
        return reservationService.getAllReservations();
    }

    //With customer
    public ReservationCustomerResponseDTO getReservationWithCustomerById(UUID id) {
        return reservationService.getReservationWithCustomerById(id);
    }

    public List<ReservationCustomerResponseDTO> getAllReservationsWithCustomer() {
        return reservationService.getAllReservationsWithCustomer();
    }

    //With rooms
    public ReservationRoomsResponseDTO getReservationWithRoomsById(UUID id) {
        return reservationService.getReservationWithRoomsById(id);
    }

    public List<ReservationRoomsResponseDTO> getAllReservationsWithRooms() {
        return reservationService.getAllReservationsWithRooms();
    }

    //With rooms and customer.
    public ReservationRoomsCustomerResponseDTO getReservationWithRoomsAndCustomerById(UUID id) {
        return reservationService.getReservationWithRoomsAndCustomerById(id);
    }

    public List<ReservationRoomsCustomerResponseDTO> getAllReservationsWithRoomsAndCustomer() {
        return reservationService.getAllReservationsWithRoomsAndCustomer();
    }

}
