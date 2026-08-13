package org.ghotel.ghotel.mapper;

import org.ghotel.ghotel.dto.request.ReservationRequestDTO;
import org.ghotel.ghotel.dto.response.ReservationCustomerResponseDTO;
import org.ghotel.ghotel.dto.response.ReservationResponseDTO;
import org.ghotel.ghotel.dto.response.ReservationRoomsCustomerResponseDTO;
import org.ghotel.ghotel.dto.response.ReservationRoomsResponseDTO;
import org.ghotel.ghotel.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class, RoomMapper.class})
public interface ReservationMapper {

    Reservation toReservationEntity(ReservationRequestDTO request);

    ReservationResponseDTO toReservationResponseDTO(Reservation reservation);

    @Mapping(source = ".", target = "reservation")
    ReservationCustomerResponseDTO toReservationCustomerResponseDTO(Reservation reservation);

    @Mapping(source = ".", target = "reservation")
    ReservationRoomsResponseDTO toReservationRoomsResponseDTO(Reservation reservation);

    @Mapping(source = ".", target = "reservation")
    ReservationRoomsCustomerResponseDTO toReservationRoomsCustomerResponseDTO(Reservation reservation);

    Reservation updateReservation(ReservationRequestDTO request, @MappingTarget Reservation reservation);

}
