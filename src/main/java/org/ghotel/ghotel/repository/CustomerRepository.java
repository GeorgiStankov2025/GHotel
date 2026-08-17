package org.ghotel.ghotel.repository;

import org.ghotel.ghotel.entity.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    //Not deleted.
    Optional<Customer> getByIdAndDeletedFalse(UUID id);

    List<Customer> getAllByDeletedFalse();

    @EntityGraph(value = "Customer.reservations")
    Optional<Customer> getWithReservationsByIdAndDeletedFalse(UUID id);

    @EntityGraph(value = "Customer.reservations")
    List<Customer> getAllWithReservationsByDeletedFalse();

    //Deleted.
    Optional<Customer> getByIdAndDeletedTrue(UUID id);
    List<Customer> getAllByDeletedTrue();

    @EntityGraph(value = "Customer.reservations")
    Optional<Customer> getWithReservationsByIdAndDeletedTrue(UUID id);

    @EntityGraph(value = "Customer.reservations")
    List<Customer> getAllWithReservationsByDeletedTrue();

    //All.
    //List<Customer> getCustomers();

    @EntityGraph(value = "Customer.reservations")
    List<Customer> findAllWithReservationsBy();
}
