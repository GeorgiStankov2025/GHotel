package org.ghotel.ghotel.repository;

import org.ghotel.ghotel.entity.Reservation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    //Not deleted.
    Optional<Reservation> getReservationByIdAndDeletedFalse(UUID id);

    List<Reservation> getReservationsByDeletedFalse();

    @EntityGraph(value = "Reservation.customer")
    Optional<Reservation> getReservationAndCustomerByIdAndDeletedFalse(UUID id);

    @EntityGraph(value = "Reservation.customer")
    List<Reservation> getReservationsAndCustomerByDeletedFalse();

    @EntityGraph(value = "Reservation.rooms")
    Optional<Reservation> getReservationAndRoomsByIdAndDeletedFalse(UUID id);

    @EntityGraph(value = "Reservation.rooms")
    List<Reservation> getReservationsAndRoomsByDeletedFalse();

    @EntityGraph(value = "Reservation.roomsAndCustomer")
    Optional<Reservation> getReservationAndRoomsAndCustomerByIdAndDeletedFalse(UUID id);

    @EntityGraph(value = "Reservation.roomsAndCustomer")
    List<Reservation> getReservationsAndRoomsAndCustomerByDeletedFalse();

    //Soft deleted.
    Optional<Reservation> getReservationByIdAndDeletedTrue(UUID id);

    List<Reservation> getReservationsByDeletedTrue();

    @EntityGraph(value = "Reservation.customer")
    Optional<Reservation> getReservationAndCustomerByIdAndDeletedTrue(UUID id);

    @EntityGraph(value = "Reservation.customer")
    List<Reservation> getReservationsAndCustomerByDeletedTrue();

    @EntityGraph(value = "Reservation.rooms")
    Optional<Reservation> getReservationAndRoomsByIdAndDeletedTrue(UUID id);

    @EntityGraph(value = "Reservation.rooms")
    List<Reservation> getReservationsAndRoomsByDeletedTrue();

    @EntityGraph(value = "Reservation.roomsAndCustomer")
    Optional<Reservation> getReservationAndRoomsAndCustomerByIdAndDeletedTrue(UUID id);

    @EntityGraph(value = "Reservation.roomsAndCustomer")
    List<Reservation> getReservationsAndRoomsAndCustomerByDeletedTrue();

    //All.
    //List<Reservation> getReservations();

    @EntityGraph(value = "Reservation.customer")
    List<Reservation> getReservationsAndCustomerBy();

    @EntityGraph(value = "Reservation.rooms")
    List<Reservation> getReservationsAndRoomsBy();

    @EntityGraph(value = "Reservation.roomsAndCustomer")
    List<Reservation> getReservationsAndRoomsAndCustomerBy();
}