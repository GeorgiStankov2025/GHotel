package org.ghotel.ghotel.service;

import jakarta.transaction.Transactional;
import org.ghotel.ghotel.dto.request.EmployeeRequest;
import org.ghotel.ghotel.dto.response.EmployeeResponse;
import org.ghotel.ghotel.entity.Employee;
import org.ghotel.ghotel.exception.EmployeeException;
import org.ghotel.ghotel.mapper.EmployeeMapper;
import org.ghotel.ghotel.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Transactional
    public EmployeeResponse addEmployee(EmployeeRequest request) {
        Employee employee = employeeMapper.toEntity(request);
        Employee saved = employeeRepository.save(employee);
        return employeeMapper.toResponse(saved);
    }


    @Transactional(rollbackOn = EmployeeException.class)
    public EmployeeResponse editEmployee(Long id, EmployeeRequest request) {

        Employee employee = findByIdUndeleted(id);
        employee = employeeMapper.changeEmployee(request, employee);
        return employeeMapper.toResponse(employee);
    }

    @Transactional(rollbackOn = EmployeeException.class)
    public EmployeeResponse deleteEmployee(Long id) {
        Employee employee = findByIdUndeleted(id);
        employee.setDeleted(true);
        return employeeMapper.toResponse(employee);
    }

    @Transactional
    public EmployeeResponse getEmployeeByIdUndeleted(Long id) {
        Employee employee = findByIdUndeleted(id);
        return employeeMapper.toResponse(employee);
    }

    private Employee findByIdUndeleted(Long id) {
        return employeeRepository.getEmployeeByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new EmployeeException("Employee not found with id: " + id));
    }

}
