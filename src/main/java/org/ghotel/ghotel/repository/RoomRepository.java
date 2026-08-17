package org.ghotel.ghotel.repository;

import org.ghotel.ghotel.entity.Room;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {
    //Deleted false.
    Optional<Room> getRoomByIdAndDeletedFalse(UUID id);

    List<Room> getAllByDeletedFalse();

    @EntityGraph(value = "Room.reservations")
    Optional<Room> getRoomAndReservationsByIdAndDeletedFalse(UUID id);

    @EntityGraph(value = "Room.reservations")
    List<Room> getAllWithReservationsByDeletedFalse();

    //Deleted true.
    Optional<Room> getRoomByIdAndDeletedTrue(UUID id);
    List<Room> getAllByDeletedTrue();

    @EntityGraph(value = "Room.reservations")
    Optional<Room> getRoomAndReservationsByIdAndDeletedTrue(UUID id);

    @EntityGraph(value = "Room.reservations")
    List<Room> getAllWithReservationsByDeletedTrue();

    //All.
    boolean existsByRoomNumber(long roomNumber);

//    List<Room> getAll();

    @EntityGraph(value = "Room.reservations")
    List<Room> getAllWithReservationsBy();

}
