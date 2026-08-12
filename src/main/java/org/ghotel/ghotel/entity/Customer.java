package org.ghotel.ghotel.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ghotel.ghotel.entity.base.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "customer")
@NamedEntityGraph(
        name = "Customer.rooms",
        includeAllAttributes = false,
        attributeNodes = @NamedAttributeNode("rooms")
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Customer extends BaseEntity {
    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    @Column(name = "details", length = 255)
    private String details;

    @Column(name = "check_in", nullable = false)
    private LocalDateTime checkIn;

    @Column(name = "check_out", nullable = false)
    private LocalDateTime checkOut;

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.DETACH, CascadeType.MERGE})
    private final List<Room> rooms = new ArrayList<>();

    public Customer(String firstName,
                    String lastName,
                    String details,
                    LocalDateTime checkIn,
                    LocalDateTime checkOut) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.details = details;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public void addRoom(Room room) {
        rooms.add(room);
        room.setCustomer(this);
        room.setTaken(true);
    }

    public void removeRoom(Room room) {
        rooms.remove(room);
        room.setCustomer(null);
        room.setTaken(false);
    }

    public List<Room> getRooms() {
        return Collections.unmodifiableList(rooms);
    }

}
