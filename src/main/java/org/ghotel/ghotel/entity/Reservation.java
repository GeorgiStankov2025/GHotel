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
    private List<Room> rooms = new ArrayList<>();

    protected Reservation(Builder builder) {
        this.details = builder.details;
        this.checkIn = builder.checkIn;
        this.checkOut = builder.checkOut;
        this.customer = builder.customer;
        this.rooms = builder.rooms;
    }

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

    public static Reservation.Builder builder() {
        return new Reservation.Builder();
    }

    public static class Builder extends BaseEntity.Builder<Reservation, Builder> {
        private String details = "defaultDetails";
        private OffsetDateTime checkIn = OffsetDateTime.now();
        private OffsetDateTime checkOut = OffsetDateTime.now().plusDays(1);
        private Customer customer = new Customer("Totio", "Totiov");
        private final List<Room> rooms = new ArrayList<>();

        public Builder details(String details) {
            this.details = details;
            return self();
        }

        public Builder checkIn(OffsetDateTime checkIn) {
            this.checkIn = checkIn;
            return self();
        }

        public Builder checkOut(OffsetDateTime checkOut) {
            this.checkOut = checkOut;
            return self();
        }

        public Builder customer(Customer customer) {
            this.customer = customer;
            return self();
        }

        public Builder room(Room room) { // 👈 Полезен helper в builder-a за Unit тестове
            this.rooms.add(room);
            return self();
        }

        @Override
        public Reservation build() {
            return new Reservation(this);
        }
    }
}
