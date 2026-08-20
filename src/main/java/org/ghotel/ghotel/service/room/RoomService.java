package org.ghotel.ghotel.service.room;

import org.ghotel.ghotel.dto.request.RoomRequestDTO;
import org.ghotel.ghotel.dto.response.DeletedDTO;
import org.ghotel.ghotel.dto.response.RoomReservationsResponseDTO;
import org.ghotel.ghotel.dto.response.RoomResponseDTO;
import org.ghotel.ghotel.entity.Room;
import org.ghotel.ghotel.exception.InvalidRequestException;
import org.ghotel.ghotel.exception.ResourceNotFoundException;
import org.ghotel.ghotel.mapper.RoomMapper;
import org.ghotel.ghotel.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class RoomService implements IRoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomService(
            RoomRepository roomRepository,
            RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    @Transactional
    @Override
    public RoomResponseDTO addRoom(RoomRequestDTO request) {
        if (roomRepository.existsByRoomNumber(request.roomNumber())) {
            throw new InvalidRequestException("Cannot add room.");
        }
        Room room = roomMapper.toRoomEntity(request);
        Room saved = roomRepository.save(room);
        return roomMapper.toRoomResponseDTO(saved);
    }

    @Override
    public RoomResponseDTO getRoomById(UUID id) {
        Room room = findRoomById(id);
        return roomMapper.toRoomResponseDTO(room);
    }

    @Override
    public List<RoomResponseDTO> getRooms() {
        List<Room> rooms = roomRepository.getAllByDeletedFalse();
        return rooms
                .stream()
                .map(roomMapper::toRoomResponseDTO)
                .toList();
    }
    //int pesho = 3;
//    Stream<Room> rooms2=rooms.stream()
//            .filter(x -> x.getRoomCapacity() == pesho)
//            .peek(x->System.out.println(x.getRoomNumber()));
//        return null;

    @Override
    public RoomReservationsResponseDTO getRoomWithReservationById(UUID id) {
        Room room = findRoomWithReservationsById(id);
        return roomMapper.toRoomReservationsResponseDTO(room);
    }

    @Override
    public List<RoomReservationsResponseDTO> getRoomsWithReservations() {
        List<Room> rooms = roomRepository.getAllWithReservationsByDeletedFalse();
        return rooms
                .stream()
                .map(roomMapper::toRoomReservationsResponseDTO)
                .toList();
    }

    @Override
    public List<RoomResponseDTO> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms
                .stream()
                .map(roomMapper::toRoomResponseDTO)
                .toList();
    }

    @Override
    public List<RoomReservationsResponseDTO> getAllRoomsWithReservations() {
        List<Room> rooms = roomRepository.getAllWithReservationsBy();
        return rooms
                .stream()
                .map(roomMapper::toRoomReservationsResponseDTO)
                .toList();
    }

    @Transactional
    @Override
    public RoomResponseDTO editRoom(UUID id, RoomRequestDTO request) {
        Room room = findRoomById(id);
        room = roomMapper.updateRoom(request, room);
        return roomMapper.toRoomResponseDTO(room);
    }

    @Transactional
    @Override
    public DeletedDTO deleteRoom(UUID id) {
        Room room = findRoomById(id);
        room.setDeleted(true);
        return new DeletedDTO("Resource deleted successfully.");
    }

    @Transactional
    @Override
    public RoomResponseDTO restoreRoom(UUID id) {
        Room room = findDeletedRoomById(id);
        room.setDeleted(false);
        return roomMapper.toRoomResponseDTO(room);
    }

    @Override
    public RoomResponseDTO getDeletedRoomById(UUID id) {
        Room room = findDeletedRoomById(id);
        return roomMapper.toRoomResponseDTO(room);
    }

//    public List<RoomResponseDTO> getDeletedRooms() {
//        List<Room> rooms = roomRepository.getAllByDeletedTrue();
//        return rooms
//                .stream()
//                .map(roomMapper::toRoomResponseDTO)
//                .toList();
//    }
//
//    public RoomReservationsResponseDTO getDeletedRoomWithReservationById(UUID id) {
//        Room room = findDeletedRoomWithReservationsById(id);
//        return roomMapper.toRoomReservationsResponseDTO(room);
//    }
//
//    public List<RoomReservationsResponseDTO> getDeletedRoomsWithReservations() {
//        List<Room> rooms = roomRepository.getAllWithReservationsByDeletedTrue();
//        return rooms
//                .stream()
//                .map(roomMapper::toRoomReservationsResponseDTO)
//                .toList();
//    }

    @Override
    public Room findRoomById(UUID id) {
        return roomRepository.getRoomByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
    }

    private Room findRoomWithReservationsById(UUID id) {
        return roomRepository.getRoomAndReservationsByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Room not found with id: " + id));
    }

    @Override
    public Room findDeletedRoomById(UUID id) {
        return roomRepository.getRoomByIdAndDeletedTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
    }

//    private Room findDeletedRoomWithReservationsById(UUID id) {
//        return roomRepository.getRoomAndReservationsByIdAndDeletedTrue(id)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("Room not found with id: " + id));
//    }
}
