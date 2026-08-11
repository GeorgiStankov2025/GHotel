package org.ghotel.ghotel.service;

import org.ghotel.ghotel.dto.request.RoomRequest;
import org.ghotel.ghotel.dto.response.RoomCustomerResponse;
import org.ghotel.ghotel.dto.response.RoomResponse;
import org.ghotel.ghotel.entity.Customer;
import org.ghotel.ghotel.entity.Room;
import org.ghotel.ghotel.exception.CustomerException;
import org.ghotel.ghotel.exception.RoomException;
import org.ghotel.ghotel.mapper.RoomMapper;
import org.ghotel.ghotel.repository.CustomerRepository;
import org.ghotel.ghotel.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
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
    public RoomResponse addRoom(RoomRequest request) {
        Room room = roomMapper.toEntity(request);
        Room saved = roomRepository.save(room);
        return roomMapper.toRoomResponse(saved);
    }

    @Transactional(readOnly = true)
    public RoomResponse getRoomById(Long id) {
        Room room = findRoomById(id);
        return roomMapper.toRoomResponse(room);
    }

    @Transactional(readOnly = true)
    public List<RoomResponse> getAllRooms() {
        List<Room> rooms = roomRepository.getAllByDeletedFalse();
        return rooms
                .stream()
                .map(roomMapper::toRoomResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoomCustomerResponse getRoomWithCustomerById(Long id) {
        Room room = roomRepository.getRoomAndCustomerByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new RoomException("Room not found with id: " + id));
        return roomMapper.toRoomCustomerResponse(room);
    }

    @Transactional(readOnly = true)
    public List<RoomCustomerResponse> getAllRoomsWithCustomer() {
        List<Room> rooms = roomRepository.getAllWithCustomerByDeletedFalse();
        return rooms
                .stream()
                .map(roomMapper::toRoomCustomerResponse)
                .toList();
    }

    @Transactional
    public RoomCustomerResponse takeRoom(Long roomId, Long customerId) {
        Customer customer = findCustomerById(customerId);
        Room room = findRoomById(roomId);
        if (room.isTaken()) {
            throw new RoomException("No free room found with id: " + roomId);
        }
        customer.addRoom(room);
        return roomMapper.toRoomCustomerResponse(room);
    }

    @Transactional
    public RoomCustomerResponse freeRoom(Long roomId, Long customerId) {
        Customer customer = findCustomerById(customerId);
        Room room = findRoomById(roomId);
        if (!room.isTaken()) {
            throw new RoomException("No taken room found with id: " + roomId);
        }
        customer.removeRoom(room);
        return roomMapper.toRoomCustomerResponse(room);
    }

    @Transactional
    public RoomResponse editRoom(Long id, RoomRequest request) {
        Room room = findRoomById(id);
        room = roomMapper.changeRoom(request, room);
        return roomMapper.toRoomResponse(room);
    }

    @Transactional
    public RoomResponse deleteRoom(Long id) {
        Room room = findRoomById(id);
        room.setDeleted(true);
        return roomMapper.toRoomResponse(room);
    }

    private Room findRoomById(Long id) {
        return roomRepository.getRoomByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RoomException("Room not found with id: " + id));
    }

    private Customer findCustomerById(Long id) {
        return customerRepository.getByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new CustomerException("Customer not found with id: " + id));
    }

}
