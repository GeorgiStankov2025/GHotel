package org.ghotel.ghotel.service;

import org.ghotel.ghotel.dto.request.ReservationRequestDTO;
import org.ghotel.ghotel.dto.response.*;
import org.ghotel.ghotel.entity.Customer;
import org.ghotel.ghotel.entity.Reservation;
import org.ghotel.ghotel.exception.ResourceNotFoundException;
import org.ghotel.ghotel.mapper.ReservationMapper;
import org.ghotel.ghotel.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public ReservationService(
            ReservationRepository reservationRepository,
            ReservationMapper reservationMapper
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    @Transactional
    public ReservationResponseDTO addReservation(ReservationRequestDTO request, Customer customer) {
        Reservation reservation = reservationMapper.toReservationEntity(request);
        reservation.setCustomer(customer);
        Reservation saved = reservationRepository.save(reservation);
        return reservationMapper.toReservationResponseDTO(saved);
    }

    //ToDo:Implement a DTO for editReservation which does not include customerId.
    @Transactional
    public ReservationResponseDTO editReservation(UUID id, ReservationRequestDTO request) {

        Reservation reservation = findById(id);
        reservation = reservationMapper.updateReservation(request, reservation);
        return reservationMapper.toReservationResponseDTO(reservation);
    }

    @Transactional
    public DeletedDTO deleteReservation(UUID id) {
        Reservation reservation = findById(id);
        reservation.setDeleted(true);
        return new DeletedDTO("Resource deleted successfully.");
    }

    @Transactional
    public ReservationResponseDTO restoreReservation(UUID id) {
        Reservation reservation = findByIdDeleted(id);
        reservation.setDeleted(false);
        return reservationMapper.toReservationResponseDTO(reservation);
    }

    //Reservation fields only
    public ReservationResponseDTO getReservationById(UUID id) {
        Reservation reservation = findById(id);
        return reservationMapper.toReservationResponseDTO(reservation);
    }

    public List<ReservationResponseDTO> getReservations() {
        List<Reservation> reservations = reservationRepository.getReservationsByDeletedFalse();
        return reservations.stream()
                .map(reservationMapper::toReservationResponseDTO)
                .toList();
    }

    //With customer
    public ReservationCustomerResponseDTO getReservationWithCustomerById(UUID id) {
        Reservation reservation = findWithCustomerById(id);
        return reservationMapper.toReservationCustomerResponseDTO(reservation);
    }

    public List<ReservationCustomerResponseDTO> getReservationsWithCustomer() {
        List<Reservation> reservations =
                reservationRepository.getReservationsAndCustomerByDeletedFalse();
        return reservations.stream()
                .map(reservationMapper::toReservationCustomerResponseDTO)
                .toList();
    }

    //With rooms
    public ReservationRoomsResponseDTO getReservationWithRoomsById(UUID id) {
        Reservation reservation = findWithRoomsById(id);
        return reservationMapper.toReservationRoomsResponseDTO(reservation);
    }

    public List<ReservationRoomsResponseDTO> getReservationsWithRooms() {
        List<Reservation> reservations =
                reservationRepository.getReservationsAndRoomsByDeletedFalse();
        return reservations.stream()
                .map(reservationMapper::toReservationRoomsResponseDTO)
                .toList();
    }

    //With rooms and customer.
    public ReservationRoomsCustomerResponseDTO getReservationWithRoomsAndCustomerById(UUID id) {
        Reservation reservation = findWithRoomsAndCustomerByI(id);
        return reservationMapper.toReservationRoomsCustomerResponseDTO(reservation);
    }

    public List<ReservationRoomsCustomerResponseDTO> getReservationsWithRoomsAndCustomer() {
        List<Reservation> reservations =
                reservationRepository.getReservationsAndRoomsAndCustomerByDeletedFalse();
        return reservations.stream()
                .map(reservationMapper::toReservationRoomsCustomerResponseDTO)
                .toList();
    }

    //All.
    public List<ReservationResponseDTO> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(reservationMapper::toReservationResponseDTO)
                .toList();
    }

    public List<ReservationCustomerResponseDTO> getAllReservationsWithCustomer() {
        List<Reservation> reservations =
                reservationRepository.getReservationsAndCustomerBy();
        return reservations.stream()
                .map(reservationMapper::toReservationCustomerResponseDTO)
                .toList();
    }

    public List<ReservationRoomsResponseDTO> getAllReservationsWithRooms() {
        List<Reservation> reservations =
                reservationRepository.getReservationsAndRoomsBy();
        return reservations.stream()
                .map(reservationMapper::toReservationRoomsResponseDTO)
                .toList();
    }

    public List<ReservationRoomsCustomerResponseDTO> getAllReservationsWithRoomsAndCustomer() {
        List<Reservation> reservations =
                reservationRepository.getReservationsAndRoomsAndCustomerBy();
        return reservations.stream()
                .map(reservationMapper::toReservationRoomsCustomerResponseDTO)
                .toList();
    }

    //Soft deleted
    //Reservation fields only
    public ReservationResponseDTO getDeletedReservationById(UUID id) {
        Reservation reservation = findByIdDeleted(id);
        return reservationMapper.toReservationResponseDTO(reservation);
    }

    public List<ReservationResponseDTO> getDeletedReservations() {
        List<Reservation> reservations = reservationRepository.getReservationsByDeletedTrue();
        return reservations.stream()
                .map(reservationMapper::toReservationResponseDTO)
                .toList();
    }

    //With customer
    public ReservationCustomerResponseDTO getDeletedReservationWithCustomerById(UUID id) {
        Reservation reservation = findWithCustomerByIdDeleted(id);
        return reservationMapper.toReservationCustomerResponseDTO(reservation);
    }

    public List<ReservationCustomerResponseDTO> getDeletedReservationsWithCustomer() {
        List<Reservation> reservations =
                reservationRepository.getReservationsAndCustomerByDeletedTrue();
        return reservations.stream()
                .map(reservationMapper::toReservationCustomerResponseDTO)
                .toList();
    }

    //With rooms
    public ReservationRoomsResponseDTO getDeletedReservationWithRoomsById(UUID id) {
        Reservation reservation = findWithRoomsByIdDeleted(id);
        return reservationMapper.toReservationRoomsResponseDTO(reservation);
    }

    public List<ReservationRoomsResponseDTO> getDeletedReservationsWithRooms() {
        List<Reservation> reservations =
                reservationRepository.getReservationsAndRoomsByDeletedTrue();
        return reservations.stream()
                .map(reservationMapper::toReservationRoomsResponseDTO)
                .toList();
    }

    //With rooms and customer.
    public ReservationRoomsCustomerResponseDTO getDeletedReservationWithRoomsAndCustomerById(UUID id) {
        Reservation reservation = findWithRoomsAndCustomerByIdDeleted(id);
        return reservationMapper.toReservationRoomsCustomerResponseDTO(reservation);
    }

    public List<ReservationRoomsCustomerResponseDTO> getDeletedReservationsWithRoomsAndCustomer() {
        List<Reservation> reservations =
                reservationRepository.getReservationsAndRoomsAndCustomerByDeletedTrue();
        return reservations.stream()
                .map(reservationMapper::toReservationRoomsCustomerResponseDTO)
                .toList();
    }

    private Reservation findById(UUID id) {
        return reservationRepository.getReservationByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation not found."));
    }

    public Reservation findWithCustomerById(UUID id) {
        return reservationRepository.getReservationAndCustomerByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation not found."));
    }

    public Reservation findWithRoomsById(UUID id) {
        return reservationRepository.getReservationAndRoomsByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation not found."));
    }

    private Reservation findWithRoomsAndCustomerByI(UUID id) {
        return reservationRepository.getReservationAndRoomsAndCustomerByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation not found."));
    }

    private Reservation findByIdDeleted(UUID id) {
        return reservationRepository.getReservationByIdAndDeletedTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation not found."));
    }

    public Reservation findWithCustomerByIdDeleted(UUID id) {
        return reservationRepository.getReservationAndCustomerByIdAndDeletedTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation not found."));
    }

    public Reservation findWithRoomsByIdDeleted(UUID id) {
        return reservationRepository.getReservationAndRoomsByIdAndDeletedTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation not found."));
    }

    private Reservation findWithRoomsAndCustomerByIdDeleted(UUID id) {
        return reservationRepository.getReservationAndRoomsAndCustomerByIdAndDeletedTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation not found."));
    }
}
