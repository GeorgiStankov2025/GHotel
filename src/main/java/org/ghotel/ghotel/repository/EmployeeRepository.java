package org.ghotel.ghotel.repository;

import org.ghotel.ghotel.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    //Not deleted
    Optional<Employee> getEmployeeByIdAndDeletedFalse(UUID id);

    List<Employee> getAllByDeletedFalse();

    //Deleted
    Optional<Employee> getEmployeeByIdAndDeletedTrue(UUID id);

    List<Employee> getAllByDeletedTrue();

    //All
    boolean existsByUsername(String username);

//    List<Employee> getAll();
}
