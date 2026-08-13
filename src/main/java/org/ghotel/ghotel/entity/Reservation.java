package org.ghotel.ghotel.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ghotel.ghotel.entity.base.BaseEntity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "reservation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "Reservation.rooms",
                attributeNodes = @NamedAttributeNode(value = "rooms")
        ),
        @NamedEntityGraph(
                name = "Reservation.customer",
                attributeNodes = @NamedAttributeNode(value = "customer")
        ),
        @NamedEntityGraph(
                name = "Reservation.roomsAndCustomer",
                attributeNodes = {
                        @NamedAttributeNode(value = "rooms"),
                        @NamedAttributeNode(value = "customer")
                }
        )
})
public class Reservation extends BaseEntity {

    @Column(name = "details", length = 255)
    private String details;

    @Column(name = "check_in", nullable = false)
    private OffsetDateTime checkIn;

    @Column(name = "check_out", nullable = false)
    private OffsetDateTime checkOut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    //ToDo: Soft delete case fix.
    // If a customer is deleted this reservation has big problems.
    private Customer customer;

    public Reservation(
            String details,
            OffsetDateTime checkIn,
            OffsetDateTime checkOut,
            Customer customer) {
        this.details = details;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.customer = customer;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reservation_rooms",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    @Setter(AccessLevel.NONE)
    //ToDo: Soft delete case fix. If a room is deleted it should not appear here.
    private List<Room> rooms = new ArrayList<>();

    public List<Room> getRooms() {
        return Collections.unmodifiableList(rooms);
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
        room.addReservation(this);
    }

    public void removeRoom(Room room) {
        this.rooms.remove(room);
        room.removeReservation(this);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.addReservation(this);
    }

    public void unsetCustomer(Customer customer) {
        this.customer = null;
        customer.removeReservation(this);
    }

    public boolean containsRoom(Room room) {
        return this.rooms.contains(room);
    }

    public boolean hasCustomer(Customer customer) {
        return this.customer.equals(customer);
    }
}
