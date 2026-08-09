package org.ghotel.ghotel.mapper;

import org.ghotel.ghotel.dto.request.CustomerRequest;
import org.ghotel.ghotel.dto.response.CustomerResponse;
import org.ghotel.ghotel.dto.response.CustomerRoomsResponse;
import org.ghotel.ghotel.dto.response.RoomResponse;
import org.ghotel.ghotel.entity.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class CustomerMapper {

    private final RoomMapper roomMapper;

    public CustomerMapper(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
    }

    public Customer toEntity(CustomerRequest request) {
        return new Customer(
                request.firstName(),
                request.lastName(),
                request.details(),
                request.checkIn(),
                request.checkIn().plusDays(request.stayDuration()),
                false
        );
    }

    public CustomerResponse toCustomerResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getDetails(),
                customer.getCheckIn(),
                customer.getCheckOut(),
                calculateStayDuration(
                        customer.getCheckIn(),
                        customer.getCheckOut()
                )
        );
    }

    public CustomerRoomsResponse toCustomerRoomsResponse(Customer customer) {
        List<RoomResponse> roomResponses = List.of();
        if (customer.getRooms() != null) {
            roomResponses = customer.getRooms().stream()
                    .map(roomMapper::toRoomResponse)
                    .toList();
        }
        return new CustomerRoomsResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getDetails(),
                customer.getCheckIn(),
                customer.getCheckOut(),
                calculateStayDuration(
                        customer.getCheckIn(),
                        customer.getCheckOut()
                ),
                roomResponses
        );
    }

    public Customer changeCustomer(Customer customer, CustomerRequest request) {
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setDetails(request.details());
        customer.setCheckIn(request.checkIn());
        customer.setCheckOut(request.checkIn().plusDays(request.stayDuration()));
        return customer;
    }

    private int calculateStayDuration(LocalDateTime checkIn, LocalDateTime checkOut) {
        int stayDuration = 0;
        if (checkIn != null && checkOut != null) {
            stayDuration = (int) ChronoUnit.DAYS.between(
                    checkIn,
                    checkOut
            );
        }
        return stayDuration;
    }

}
