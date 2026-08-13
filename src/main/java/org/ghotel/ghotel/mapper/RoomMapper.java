package org.ghotel.ghotel.mapper;

import org.ghotel.ghotel.dto.request.RoomRequestDTO;
import org.ghotel.ghotel.dto.response.RoomReservationsResponseDTO;
import org.ghotel.ghotel.dto.response.RoomResponseDTO;
import org.ghotel.ghotel.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    Room toRoomEntity(RoomRequestDTO request);

    RoomResponseDTO toRoomResponseDTO(Room room);

    @Mapping(source = ".", target = "room")
    RoomReservationsResponseDTO toRoomReservationsResponseDTO(Room room);
    
    Room updateRoom(RoomRequestDTO request, @MappingTarget Room room);
}
