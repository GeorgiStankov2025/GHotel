package org.ghotel.ghotel.repository;

import org.ghotel.ghotel.entity.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    public Optional<Customer> getByIdAndDeletedFalse(Long id);

    public List<Customer> getAllByDeletedFalse();

    public Optional<Customer> getByIdAndDeletedTrue(Long id);

    public List<Customer> getAllByDeletedTrue();

    @EntityGraph(value = "Customer.rooms")
    public Optional<Customer> getWithRoomsByIdAndDeletedFalse(Long id);

    @EntityGraph(value = "Customer.rooms")
    public List<Customer> getAllWithRoomsByDeletedFalse();

    @EntityGraph(value = "Customer.rooms")
    public Optional<Customer> getWithRoomsByIdAndDeletedTrue(Long id);

    @EntityGraph(value = "Customer.rooms")
    public List<Customer> getAllWithRoomsByDeletedTrue();
}
