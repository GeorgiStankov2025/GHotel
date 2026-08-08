package org.ghotel.ghotel.repository;

import org.ghotel.ghotel.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    public Optional<Employee> getEmployeeByIdAndDeletedFalse(Long id);

    public List<Employee> getAllByDeletedFalse();

    public Optional<Employee> getEmployeeByIdAndDeletedTrue(Long id);

    public List<Employee> getAllByDeletedTrue();

}
