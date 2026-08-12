package org.ghotel.ghotel.mapper;

import org.ghotel.ghotel.dto.request.EmployeeRequest;
import org.ghotel.ghotel.dto.response.EmployeeResponse;
import org.ghotel.ghotel.entity.Employee;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EmployeeMapper {
    public Employee toEntity(EmployeeRequest request) {
        return new Employee(
                request.firstName(),
                request.lastName(),
                request.username(),
                request.password()
        );
    }

    public Employee changeEmployee(EmployeeRequest request, Employee employee) {
        employee.setUsername(request.username());
        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setPassword(request.password());
        employee.setUpdatedAt(LocalDateTime.now());
        return employee;
    }

    public EmployeeResponse toResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getUsername(),
                employee.getFirstName(),
                employee.getLastName()
        );
    }

}
