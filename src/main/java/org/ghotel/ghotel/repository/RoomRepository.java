package org.ghotel.ghotel.repository;

import org.ghotel.ghotel.entity.Room;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    public Optional<Room> getRoomByIdAndDeletedFalse(Long id);

    public List<Room> getAllByDeletedFalse();

    public Optional<Room> getRoomByIdAndDeletedTrue(Long id);

    public List<Room> getAllByDeletedTrue();

    @EntityGraph(value = "Room.customer")
    public Optional<Room> getRoomAndCustomerByIdAndDeletedFalse(Long id);

    @EntityGraph(value = "Room.customer")
    public List<Room> getAllWithCustomerByDeletedFalse();

    @EntityGraph(value = "Room.customer")
    public Optional<Room> getRoomAndCustomerByIdAndDeletedTrue(Long id);

    @EntityGraph(value = "Room.customer")
    public List<Room> getAllWithCustomerByDeletedTrue();

}
