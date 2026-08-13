package org.ghotel.ghotel.mapper;

import org.ghotel.ghotel.dto.request.CustomerRequestDTO;
import org.ghotel.ghotel.dto.response.CustomerReservationsResponseDTO;
import org.ghotel.ghotel.dto.response.CustomerResponseDTO;
import org.ghotel.ghotel.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toCustomerEntity(CustomerRequestDTO request);

    CustomerResponseDTO toCustomerResponseDTO(Customer customer);

    @Mapping(source = ".", target = "customer")
    CustomerReservationsResponseDTO toCustomerReservationsResponseDTO(Customer customer);

    Customer updateCustomer(CustomerRequestDTO request, @MappingTarget Customer customer);
}
