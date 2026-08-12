package org.ghotel.ghotel.service;

import org.ghotel.ghotel.dto.request.RoomRequestDTO;
import org.ghotel.ghotel.dto.response.RoomReservationsResponseDTO;
import org.ghotel.ghotel.dto.response.RoomResponseDTO;
import org.ghotel.ghotel.entity.Customer;
import org.ghotel.ghotel.entity.Room;
import org.ghotel.ghotel.exception.CustomerException;
import org.ghotel.ghotel.exception.RoomException;
import org.ghotel.ghotel.repository.CustomerRepository;
import org.ghotel.ghotel.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class RoomService {
    private final RoomRepository roomRepository;
    private final CustomerRepository customerRepository;
    private final RoomMapper roomMapper;

    public RoomService(
            RoomRepository roomRepository,
            CustomerRepository customerRepository,
            RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.customerRepository = customerRepository;
        this.roomMapper = roomMapper;
    }

    @Transactional
    public RoomResponseDTO addRoom(RoomRequestDTO request) {
        Room room = roomMapper.toEntity(request);
        Room saved = roomRepository.save(room);
        return roomMapper.toRoomResponse(saved);
    }

    public RoomResponseDTO getRoomById(UUID id) {
        Room room = findRoomById(id);
        return roomMapper.toRoomResponse(room);
    }

    public List<RoomResponseDTO> getAllRooms() {
        List<Room> rooms = roomRepository.getAllByDeletedFalse();
        return rooms
                .stream()
                .map(roomMapper::toRoomResponse)
                .toList();
    }

    public RoomReservationsResponseDTO getRoomWithCustomerById(UUID id) {
        Room room = findRoomWithCustomerById(id);
        return roomMapper.toRoomCustomerResponse(room);
    }

    public List<RoomReservationsResponseDTO> getAllRoomsWithCustomer() {
        List<Room> rooms = roomRepository.getAllWithCustomerByDeletedFalse();
        return rooms
                .stream()
                .map(roomMapper::toRoomCustomerResponse)
                .toList();
    }

    @Transactional
    public RoomReservationsResponseDTO takeRoom(UUID roomId, UUID customerId) {
        Customer customer = findCustomerById(customerId);
        Room room = findRoomById(roomId);
        if (room.isTaken()) {
            throw new RoomException("No free room found.");
        }
        customer.addRoom(room);
        return roomMapper.toRoomCustomerResponse(room);
    }

    @Transactional
    public RoomReservationsResponseDTO freeRoom(UUID roomId, UUID customerId) {
        Customer customer = findCustomerById(customerId);
        Room room = findRoomWithCustomerById(roomId);
        if (!room.isTaken()) {
            throw new RoomException("No taken room found");
        }
        if (!room.getCustomer().equals(customer)) {
            throw new RoomException("Invalid customer data.");
        }
        customer.removeRoom(room);
        return roomMapper.toRoomCustomerResponse(room);
    }

    @Transactional
    public RoomResponseDTO editRoom(UUID id, RoomRequestDTO request) {
        Room room = findRoomById(id);
        room = roomMapper.changeRoom(request, room);
        return roomMapper.toRoomResponse(room);
    }

    @Transactional
    public RoomResponseDTO deleteRoom(UUID id) {
        Room room = findRoomById(id);
        room.setDeleted(true);
        return roomMapper.toRoomResponse(room);
    }

    private Room findRoomById(UUID id) {
        return roomRepository.getRoomByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RoomException("Room not found."));
    }

    private Customer findCustomerById(UUID id) {
        return customerRepository.getByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new CustomerException("Customer not found."));
    }

    private Room findRoomWithCustomerById(UUID id) {
        return roomRepository.getRoomAndCustomerByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new RoomException("Room not found."));
    }

}
