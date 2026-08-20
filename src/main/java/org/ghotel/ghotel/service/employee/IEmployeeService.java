package org.ghotel.ghotel.service.employee;

import org.ghotel.ghotel.dto.request.EmployeeRequestDTO;
import org.ghotel.ghotel.dto.response.DeletedDTO;
import org.ghotel.ghotel.dto.response.EmployeeResponseDTO;
import org.ghotel.ghotel.entity.Employee;

import java.util.List;
import java.util.UUID;

public interface IEmployeeService {
    EmployeeResponseDTO addEmployee(EmployeeRequestDTO request);

    EmployeeResponseDTO getEmployeeById(UUID id);

    List<EmployeeResponseDTO> getEmployees();

    List<EmployeeResponseDTO> getAllEmployees();

    EmployeeResponseDTO getDeletedEmployeeById(UUID id);

    EmployeeResponseDTO editEmployee(UUID id, EmployeeRequestDTO request);

    DeletedDTO deleteEmployee(UUID id);

    EmployeeResponseDTO restoreEmployee(UUID id);

    Employee findById(UUID id);

    Employee findByIdDeleted(UUID id);
}
