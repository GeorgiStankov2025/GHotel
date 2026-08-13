package org.ghotel.ghotel.service;

import org.ghotel.ghotel.dto.request.RoomRequestDTO;
import org.ghotel.ghotel.dto.response.DeletedDTO;
import org.ghotel.ghotel.dto.response.RoomReservationsResponseDTO;
import org.ghotel.ghotel.dto.response.RoomResponseDTO;
import org.ghotel.ghotel.entity.Room;
import org.ghotel.ghotel.exception.ResourceNotFoundException;
import org.ghotel.ghotel.mapper.RoomMapper;
import org.ghotel.ghotel.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomService(
            RoomRepository roomRepository,
            RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    //ToDo: Room number must be unique. Implement logic for that.
    @Transactional
    public RoomResponseDTO addRoom(RoomRequestDTO request) {
        Room room = roomMapper.toRoomEntity(request);
        Room saved = roomRepository.save(room);
        return roomMapper.toRoomResponseDTO(saved);
    }

    public RoomResponseDTO getRoomById(UUID id) {
        Room room = findRoomById(id);
        return roomMapper.toRoomResponseDTO(room);
    }

    public List<RoomResponseDTO> getAllRooms() {
        List<Room> rooms = roomRepository.getAllByDeletedFalse();
        return rooms
                .stream()
                .map(roomMapper::toRoomResponseDTO)
                .toList();
    }

    public RoomReservationsResponseDTO getRoomWithReservationById(UUID id) {
        Room room = findRoomWithReservationsById(id);
        return roomMapper.toRoomReservationsResponseDTO(room);
    }

    public List<RoomReservationsResponseDTO> getAllRoomsWithReservations() {
        List<Room> rooms = roomRepository.getAllWithReservationsByDeletedFalse();
        return rooms
                .stream()
                .map(roomMapper::toRoomReservationsResponseDTO)
                .toList();
    }

    @Transactional
    public RoomResponseDTO editRoom(UUID id, RoomRequestDTO request) {
        Room room = findRoomById(id);
        room = roomMapper.updateRoom(request, room);
        return roomMapper.toRoomResponseDTO(room);
    }

    @Transactional
    public DeletedDTO deleteRoom(UUID id) {
        Room room = findRoomById(id);
        room.setDeleted(true);
        return new DeletedDTO("Resource deleted successfully.");
    }

    public Room findRoomById(UUID id) {
        return roomRepository.getRoomByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found."));
    }

    private Room findRoomWithReservationsById(UUID id) {
        return roomRepository.getRoomAndReservationsByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Room not found."));
    }

}
