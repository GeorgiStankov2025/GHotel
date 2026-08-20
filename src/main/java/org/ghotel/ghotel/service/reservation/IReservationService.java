package org.ghotel.ghotel.service.reservation;

import org.ghotel.ghotel.dto.request.ReservationRequestDTO;
import org.ghotel.ghotel.dto.response.DeletedDTO;
import org.ghotel.ghotel.dto.response.ReservationResponseDTO;
import org.ghotel.ghotel.dto.response.ReservationRoomsCustomerResponseDTO;
import org.ghotel.ghotel.entity.Customer;
import org.ghotel.ghotel.entity.Reservation;

import java.util.List;
import java.util.UUID;

public interface IReservationService {
    ReservationResponseDTO addReservation(ReservationRequestDTO request, Customer customer);

    ReservationResponseDTO getReservationById(UUID id);

    List<ReservationResponseDTO> getReservations();

    ReservationRoomsCustomerResponseDTO getReservationWithRoomsAndCustomerById(UUID id);

    List<ReservationRoomsCustomerResponseDTO> getReservationsWithRoomsAndCustomer();

    //All.
    List<ReservationResponseDTO> getAllReservations();

    List<ReservationRoomsCustomerResponseDTO> getAllReservationsWithRoomsAndCustomer();

    ReservationResponseDTO getDeletedReservationById(UUID id);

    ReservationResponseDTO editReservation(UUID id, ReservationRequestDTO request);

    DeletedDTO deleteReservation(UUID id);

    ReservationResponseDTO restoreReservation(UUID id);

    Reservation findWithCustomerById(UUID id);

    Reservation findWithRoomsById(UUID id);
}
