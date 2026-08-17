package org.ghotel.ghotel.application;

import org.ghotel.ghotel.dto.request.ReservationCustomerRequestDTO;
import org.ghotel.ghotel.dto.request.ReservationRequestDTO;
import org.ghotel.ghotel.dto.request.ReservationRoomRequestDTO;
import org.ghotel.ghotel.dto.response.ReservationCustomerResponseDTO;
import org.ghotel.ghotel.dto.response.ReservationResponseDTO;
import org.ghotel.ghotel.dto.response.ReservationRoomsResponseDTO;
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

}
