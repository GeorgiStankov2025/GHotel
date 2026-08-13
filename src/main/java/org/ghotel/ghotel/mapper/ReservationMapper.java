package org.ghotel.ghotel.mapper;

import org.ghotel.ghotel.dto.request.ReservationRequestDTO;
import org.ghotel.ghotel.dto.response.ReservationCustomerResponseDTO;
import org.ghotel.ghotel.dto.response.ReservationResponseDTO;
import org.ghotel.ghotel.dto.response.ReservationRoomsCustomerResponseDTO;
import org.ghotel.ghotel.dto.response.ReservationRoomsResponseDTO;
import org.ghotel.ghotel.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    Reservation toReservationEntity(ReservationRequestDTO request);

    ReservationResponseDTO toReservationResponseDTO(Reservation reservation);

    ReservationCustomerResponseDTO toReservationCustomerResponseDTO(Reservation reservation);

    ReservationRoomsResponseDTO toReservationRoomsResponseDTO(Reservation reservation);

    ReservationRoomsCustomerResponseDTO toReservationRoomsCustomerResponseDTO(Reservation reservation);

    Reservation updateReservation(ReservationRequestDTO request, @MappingTarget Reservation reservation);
}
