package org.ghotel.ghotel.service.room;

import org.ghotel.ghotel.dto.request.RoomRequestDTO;
import org.ghotel.ghotel.dto.response.DeletedDTO;
import org.ghotel.ghotel.dto.response.RoomReservationsResponseDTO;
import org.ghotel.ghotel.dto.response.RoomResponseDTO;
import org.ghotel.ghotel.entity.Room;

import java.util.List;
import java.util.UUID;

public interface IRoomService {
    RoomResponseDTO addRoom(RoomRequestDTO request);

    RoomResponseDTO getRoomById(UUID id);

    List<RoomResponseDTO> getRooms();

    RoomReservationsResponseDTO getRoomWithReservationById(UUID id);

    List<RoomReservationsResponseDTO> getRoomsWithReservations();

    List<RoomResponseDTO> getAllRooms();

    List<RoomReservationsResponseDTO> getAllRoomsWithReservations();

    RoomResponseDTO getDeletedRoomById(UUID id);

    RoomResponseDTO editRoom(UUID id, RoomRequestDTO request);

    DeletedDTO deleteRoom(UUID id);

    RoomResponseDTO restoreRoom(UUID id);

    Room findRoomById(UUID id);

    Room findDeletedRoomById(UUID id);
}
