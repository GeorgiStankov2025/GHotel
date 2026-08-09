package org.ghotel.ghotel.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "customer")
@NamedEntityGraph(
        name = "Customer.rooms",
        includeAllAttributes = false,
        attributeNodes = @NamedAttributeNode("rooms")
)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_generator")
    @SequenceGenerator(
            name = "customer_generator",
            sequenceName = "customer_sequence",
            allocationSize = 1,
            initialValue = 1
    )
    @Column(name = "customer_id")
    private Long id;

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

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.DETACH, CascadeType.MERGE})
    private final List<Room> rooms = new ArrayList<>();

    protected Customer() {
    }

    public Customer(String firstName, String lastName, String details, LocalDateTime checkIn, LocalDateTime checkOut, boolean deleted) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.details = details;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void addRoom(Room room) {
        if (room == null) return;
        rooms.add(room);
        room.setCustomer(this);
        room.setTaken(true);
    }

    public void removeRoom(Room room) {
        if (room == null) return;
        rooms.remove(room);
        room.setCustomer(null);
        room.setTaken(false);
    }

    public List<Room> getRooms() {
        return Collections.unmodifiableList(rooms);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Customer customer)) return false;
        return Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
