package org.ghotel.ghotel.service;

import org.ghotel.ghotel.dto.request.RoomRequestDTO;
import org.ghotel.ghotel.dto.response.RoomResponseDTO;
import org.ghotel.ghotel.entity.Room;
import org.ghotel.ghotel.exception.InvalidRequestException;
import org.ghotel.ghotel.exception.ResourceNotFoundException;
import org.ghotel.ghotel.mapper.RoomMapper;
import org.ghotel.ghotel.repository.RoomRepository;
import org.ghotel.ghotel.service.room.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    RoomRepository roomRepository;

    @Spy
    RoomMapper roomMapper = Mappers.getMapper(RoomMapper.class);

    @InjectMocks
    RoomService roomService;

    UUID id;
    RoomRequestDTO addRequest, updateRequest;
    RoomResponseDTO addResponse, updateResponse, getResponse;
    Room addedRoom, foundRoom;
    ResourceNotFoundException notFoundException;
    InvalidRequestException invalidRequestException;

    @BeforeEach
    void setUp() {
        id = UUID.fromString("01a01482-6f95-7472-bae6-8279297087c2");
        addRequest = new RoomRequestDTO(10, 4);
        updateRequest = new RoomRequestDTO(12, 3);
        addResponse = new RoomResponseDTO(id, 4, 10);
        updateResponse = new RoomResponseDTO(id, 3, 12);
        addedRoom = new Room(10, 4);
        invalidRequestException = new InvalidRequestException("Cannot add room.");
        getResponse = new RoomResponseDTO(id, 4, 10);
        foundRoom = new Room(10, 3);
    }

    @Test
    void addRoom() {
        when(roomRepository.existsByRoomNumber(addRequest.roomNumber()))
                .thenReturn(false);
        when(roomRepository.save(any(Room.class)))
                .thenReturn(addedRoom);
        RoomResponseDTO response = roomService.addRoom(addRequest);

        assertNotNull(response);
        assertEquals(addResponse.roomNumber(), response.roomNumber());
        assertEquals(addResponse.roomCapacity(), response.roomCapacity());
    }

    @Test
    void addRoom_ThrowsInvalidRequestException_RoomAlreadyExistsWithThisNumber() {
        when(roomRepository.existsByRoomNumber(addRequest.roomNumber()))
                .thenReturn(true);
        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> roomService.addRoom(addRequest));
        assertEquals(invalidRequestException.getMessage(), ex.getMessage());
    }

    @Test
    void getRoomById_Successful() {
        //Should return existing object with the id.
        when(roomRepository.getRoomByIdAndDeletedFalse(id))
                .thenReturn(Optional.of(foundRoom));

        Room room = roomService.findRoomById(id);
        assertAll(
                () -> assertNotNull(room),
                () -> assertEquals(foundRoom.getRoomNumber(), room.getRoomNumber()),
                () -> assertEquals(foundRoom.getRoomCapacity(), room.getRoomCapacity())
        );
    }

    @Test
    void getRoom_Unsuccessful() {
        //Should throw a ResourceNotFoundException.
        UUID id = UUID.fromString("01a01482-6f95-7472-bae6-8279297087c2");
        when(roomRepository.getRoomByIdAndDeletedFalse(id))
                .thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> roomService.findRoomById(id)
        );
        assertEquals("Room not found with id: " + id, ex.getMessage());
    }
}
