package org.ghotel.ghotel.repository;

import org.ghotel.ghotel.entity.Room;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {

    Optional<Room> getRoomByIdAndDeletedFalse(UUID id);

    List<Room> getAllByDeletedFalse();

    @EntityGraph(value = "Room.customer")
    Optional<Room> getRoomAndCustomerByIdAndDeletedFalse(UUID id);

    @EntityGraph(value = "Room.customer")
    List<Room> getAllWithCustomerByDeletedFalse();

    Optional<Room> getRoomByIdAndDeletedTrue(UUID id);

    List<Room> getAllByDeletedTrue();

    @EntityGraph(value = "Room.customer")
    Optional<Room> getRoomAndCustomerByIdAndDeletedTrue(UUID id);

    @EntityGraph(value = "Room.customer")
    List<Room> getAllWithCustomerByDeletedTrue();

}
