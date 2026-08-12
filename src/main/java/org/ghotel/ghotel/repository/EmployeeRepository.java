package org.ghotel.ghotel.repository;

import org.ghotel.ghotel.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    Optional<Employee> getEmployeeByIdAndDeletedFalse(UUID id);

    List<Employee> getAllByDeletedFalse();

    boolean existsByUsername(String username);

    Optional<Employee> getEmployeeByIdAndDeletedTrue(UUID id);

    List<Employee> getAllByDeletedTrue();

}
