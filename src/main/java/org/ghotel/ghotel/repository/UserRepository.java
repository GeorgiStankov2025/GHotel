package org.ghotel.ghotel.repository;

import org.ghotel.ghotel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    //Not deleted
    Optional<User> getUserByIdAndDeletedFalse(UUID id);

    List<User> getAllByDeletedFalse();

    //Deleted
    Optional<User> getUserByIdAndDeletedTrue(UUID id);

    List<User> getAllByDeletedTrue();

    //All
    boolean existsByUsername(String username);

//    List<Employee> getAll();

    Optional<User> getUserByUsernameAndDeletedFalse(String username);
}
