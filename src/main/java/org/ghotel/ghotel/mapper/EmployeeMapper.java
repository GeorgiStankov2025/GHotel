package org.ghotel.ghotel.mapper;

import org.ghotel.ghotel.dto.request.EmployeeRequestDTO;
import org.ghotel.ghotel.dto.response.EmployeeResponseDTO;
import org.ghotel.ghotel.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = true))
public interface EmployeeMapper {
    Employee toEmployeeEntity(EmployeeRequestDTO request);

    EmployeeResponseDTO toEmployeeResponseDTO(Employee employee);

//    Employee updateEmployee(EmployeeRequestDTO request, @MappingTarget Employee employee);
}