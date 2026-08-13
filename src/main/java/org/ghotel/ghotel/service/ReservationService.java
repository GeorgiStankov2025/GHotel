package org.ghotel.ghotel.service;

import org.ghotel.ghotel.dto.request.ReservationRequestDTO;
import org.ghotel.ghotel.dto.response.*;
import org.ghotel.ghotel.entity.Reservation;
import org.ghotel.ghotel.exception.ResourceNotFoundException;
import org.ghotel.ghotel.mapper.ReservationMapper;
import org.ghotel.ghotel.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public ReservationService(ReservationRepository reservationRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    @Transactional
    public ReservationResponseDTO addReservation(ReservationRequestDTO request) {
        Reservation reservation = reservationMapper.toReservationEntity(request);
        Reservation saved = reservationRepository.save(reservation);
        return reservationMapper.toReservationResponseDTO(saved);
    }


    @Transactional
    public ReservationResponseDTO editReservation(UUID id, ReservationRequestDTO request) {

        Reservation reservation = findByIdUndeleted(id);
        reservation = reservationMapper.updateReservation(request, reservation);
        return reservationMapper.toReservationResponseDTO(reservation);
    }

    @Transactional
    public DeletedDTO deleteReservation(UUID id) {
        Reservation reservation = findByIdUndeleted(id);
        reservation.setUpdatedAt(OffsetDateTime.now());
        reservation.setDeleted(true);
        return new DeletedDTO("Resource deleted successfully.");
    }

    //Reservation fields only
    public ReservationResponseDTO getReservationById(UUID id) {
        Reservation reservation = findByIdUndeleted(id);
        return reservationMapper.toReservationResponseDTO(reservation);
    }

    public List<ReservationResponseDTO> getAllReservations() {
        List<Reservation> reservations = reservationRepository.getReservationsByDeletedFalse();
        return reservations.stream()
                .map(reservationMapper::toReservationResponseDTO)
                .toList();
    }

    //With customer
    public ReservationCustomerResponseDTO getReservationWithCustomerById(UUID id) {
        Reservation reservation = findWithCustomerByIdUndeleted(id);
        return reservationMapper.toReservationCustomerResponseDTO(reservation);
    }

    public List<ReservationCustomerResponseDTO> getAllReservationsWithCustomer() {
        List<Reservation> reservations = reservationRepository.getReservationsAndCustomerByDeletedFalse();
        return reservations.stream()
                .map(reservationMapper::toReservationCustomerResponseDTO)
                .toList();
    }

    //With rooms
    public ReservationRoomsResponseDTO getReservationWithRoomsById(UUID id) {
        Reservation reservation = findWithRoomsByIdUndeleted(id);
        return reservationMapper.toReservationRoomsResponseDTO(reservation);
    }

    public List<ReservationRoomsResponseDTO> getAllReservationsWithRooms() {
        List<Reservation> reservations = reservationRepository.getReservationsAndRoomsByDeletedFalse();
        return reservations.stream()
                .map(reservationMapper::toReservationRoomsResponseDTO)
                .toList();
    }

    //With rooms and customer.
    public ReservationRoomsCustomerResponseDTO getReservationWithRoomsAndCustomerById(UUID id) {
        Reservation reservation = findWithRoomsAndCustomerByIdUndeleted(id);
        return reservationMapper.toReservationRoomsCustomerResponseDTO(reservation);
    }

    public List<ReservationRoomsCustomerResponseDTO> getAllReservationsWithRoomsAndCustomer() {
        List<Reservation> reservations = reservationRepository.getReservationsAndRoomsAndCustomerByDeletedFalse();
        return reservations.stream()
                .map(reservationMapper::toReservationRoomsCustomerResponseDTO)
                .toList();
    }

    /*
    ToDo:
     Add room add and remove logic, as well as customer add and remove logic.
     */


    private Reservation findByIdUndeleted(UUID id) {
        return reservationRepository.getReservationByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation not found."));
    }

    public Reservation findWithCustomerByIdUndeleted(UUID id) {
        return reservationRepository.getReservationAndCustomerByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation not found."));
    }

    public Reservation findWithRoomsByIdUndeleted(UUID id) {
        return reservationRepository.getReservationAndRoomsByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation not found."));
    }

    private Reservation findWithRoomsAndCustomerByIdUndeleted(UUID id) {
        return reservationRepository.getReservationAndRoomsAndCustomerByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation not found."));
    }
}
