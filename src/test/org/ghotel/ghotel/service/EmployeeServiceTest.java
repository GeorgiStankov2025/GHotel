package org.ghotel.ghotel.service;

import org.ghotel.ghotel.dto.request.EmployeeRequestDTO;
import org.ghotel.ghotel.dto.response.DeletedDTO;
import org.ghotel.ghotel.dto.response.EmployeeResponseDTO;
import org.ghotel.ghotel.entity.Employee;
import org.ghotel.ghotel.exception.InvalidRequestException;
import org.ghotel.ghotel.exception.ResourceNotFoundException;
import org.ghotel.ghotel.mapper.EmployeeMapper;
import org.ghotel.ghotel.repository.EmployeeRepository;
import org.ghotel.ghotel.service.employee.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {


    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);

    @InjectMocks
    private EmployeeService employeeService;

    EmployeeRequestDTO request;
    Employee employee;
    Employee updatedEmployee;
    EmployeeResponseDTO expectedResponseAdded;
    EmployeeResponseDTO expectedResponseUpdated;
    EmployeeRequestDTO updateRequest;
    DeletedDTO deletedResponse;
    UUID id;
    Employee deletedEmployee;

    @BeforeEach
    void setUp() {
        request = new EmployeeRequestDTO(
                "Ivancho123",
                "Ivan",
                "Draganov",
                "123456"
        );

//        employee = new Employee(
//                "Ivan",
//                "Draganov",
//                "Ivancho123",
//                "123456"
//        );

        employee = Employee.builder()
                .id(UUID.randomUUID())
                .firstName("Ivan")
                .lastName("Draganov")
                .username("Ivancho123")
                .build();

        expectedResponseAdded = new EmployeeResponseDTO(
                UUID.randomUUID(),
                "Ivancho123",
                "Ivan",
                "Draganov"
        );

//        updatedEmployee = new Employee(
//                "Dragan",
//                "Ivanov",
//                "Dragancho123",
//                "123456"
//        );

        updatedEmployee = Employee.builder()
                .firstName("Dragan")
                .lastName("Ivanov")
                .username("Dragancho123")
                .build();

        expectedResponseUpdated = new EmployeeResponseDTO(
                UUID.randomUUID(),
                "Dragancho123",
                "Dragan",
                "Ivanov"
        );

        updateRequest = new EmployeeRequestDTO(
                "Dragancho123",
                "Dragan",
                "Ivanov",
                "123456"
        );

        id = UUID.fromString("01a01482-6f95-7472-bae6-8279297087c6");

        deletedResponse = new DeletedDTO("Resource deleted successfully.");

        deletedEmployee = Employee.builder()
                .deleted(true)
                .build();
    }

    @Test
    void getEmployeeById_Successful() {
        Employee mockEmployee = Employee.builder()
                .firstName("Georgi")
                .build();
        //Should return existing object with the id.
        when(employeeRepository.getEmployeeByIdAndDeletedFalse(id))
                .thenReturn(Optional.of(mockEmployee));

        Employee employee = employeeService.findById(id);
        assertNotNull(employee);
        assertEquals(mockEmployee.getFirstName(), employee.getFirstName());
    }

    @Test
    void getEmployee_Unsuccessful() {
        //Should throw a ResourceNotFoundException.
        UUID id = UUID.fromString("01a01482-6f95-7472-bae6-8279297087c2");
        when(employeeRepository.getEmployeeByIdAndDeletedFalse(id))
                .thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.findById(id)
        );
        assertEquals("Employee not found with id: " + id, ex.getMessage());
    }

    @Test
    void addEmployee_Successful() {
        //Should add a new employee successfully.
        when(employeeRepository.existsByUsername(request.username()))
                .thenReturn(false);
        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(employee);
        EmployeeResponseDTO response = employeeService.addEmployee(request);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(expectedResponseAdded.username(), response.username()),
                () -> assertEquals(expectedResponseAdded.firstName(), response.firstName()),
                () -> assertEquals(expectedResponseAdded.lastName(), response.lastName())
        );
    }

    @Test
    void addEmployee_ThrowsInvalidRequestException_UsernameAlreadyExists() {
        //Should throw InvalidRequestException
        when(employeeRepository.existsByUsername(request.username()))
                .thenReturn(true);
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                () -> employeeService.addEmployee(request));
        assertEquals("Employee with username: " + request.username() + " already exists.",exception.getMessage());
        verify(employeeMapper, times(0)).toEmployeeEntity(request);
    }

    @Test
    void editEmployee_Successful() {
        when(employeeRepository.getEmployeeByIdAndDeletedFalse(id))
                .thenReturn(Optional.of(updatedEmployee));

        EmployeeResponseDTO response = employeeService.editEmployee(id, updateRequest);
//        EmployeeResponseDTO response = employeeService.editEmployee(id, request);
        assertAll(
                () -> assertEquals(expectedResponseUpdated.username(), response.username()),
                () -> assertEquals(expectedResponseUpdated.firstName(), response.firstName()),
                () -> assertEquals(expectedResponseUpdated.lastName(), response.lastName())
        );
    }

    @Test
    void editEmployee_ThrowsResourceNotFoundException_NoEmployeeFound() {
        when(employeeRepository.getEmployeeByIdAndDeletedFalse(id))
                .thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> employeeService.editEmployee(id, request));
        assertEquals("Employee not found with id: " + id, exception.getMessage());
        verify(employeeMapper, times(0)).toEmployeeEntity(request);
    }

    @Test
    void deleteEmployee_Successful() {
        when(employeeRepository.getEmployeeByIdAndDeletedFalse(id))
                .thenReturn(Optional.of(employee));
        DeletedDTO response = employeeService.deleteEmployee(id);
        assertNotNull(response);
        assertEquals(response.message(), deletedResponse.message());
    }

    @Test
    void deleteEmployee_ThrowsResourceNotFoundException_NoEmployeeFound() {
        when(employeeRepository.getEmployeeByIdAndDeletedFalse(id))
                .thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> employeeService.deleteEmployee(id));
        assertEquals("Employee not found with id: " + id, exception.getMessage());
        verify(employeeMapper, times(0)).toEmployeeEntity(request);
    }

}
