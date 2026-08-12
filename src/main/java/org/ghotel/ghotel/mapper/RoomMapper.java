package org.ghotel.ghotel.mapper;

import org.ghotel.ghotel.dto.request.RoomRequest;
import org.ghotel.ghotel.dto.response.RoomCustomerResponse;
import org.ghotel.ghotel.dto.response.RoomResponse;
import org.ghotel.ghotel.entity.Customer;
import org.ghotel.ghotel.entity.Room;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class RoomMapper {
    public Room toEntity(RoomRequest request) {
        return new Room(
                request.roomCapacity(),
                false
        );
    }

    public Room changeRoom(RoomRequest request, Room room) {
        room.setRoomCapacity(request.roomCapacity());
        room.setUpdatedAt(LocalDateTime.now());
        return room;
    }

    public RoomResponse toRoomResponse(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getRoomCapacity(),
                room.isTaken()
        );
    }

    public RoomCustomerResponse toRoomCustomerResponse(Room room) {
        if (room == null) return null;

        UUID customerId = null;
        String customerName = null;

        if (room.getCustomer() != null) {
            Customer customer = room.getCustomer();
            customerId = customer.getId();
            customerName = customer.getFirstName() + " " + customer.getLastName();
        }
        return new RoomCustomerResponse(
                room.getId(),
                room.getRoomCapacity(),
                customerId,
                customerName
        );
    }
}
