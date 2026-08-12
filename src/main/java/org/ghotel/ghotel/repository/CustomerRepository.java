package org.ghotel.ghotel.repository;

import org.ghotel.ghotel.entity.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> getByIdAndDeletedFalse(UUID id);

    List<Customer> getAllByDeletedFalse();

    @EntityGraph(value = "Customer.rooms")
    Optional<Customer> getWithRoomsByIdAndDeletedFalse(UUID id);

    @EntityGraph(value = "Customer.rooms")
    List<Customer> getAllWithRoomsByDeletedFalse();

    Optional<Customer> getByIdAndDeletedTrue(UUID id);

    List<Customer> getAllByDeletedTrue();

    @EntityGraph(value = "Customer.rooms")
    Optional<Customer> getWithRoomsByIdAndDeletedTrue(UUID id);

    @EntityGraph(value = "Customer.rooms")
    List<Customer> getAllWithRoomsByDeletedTrue();
}
