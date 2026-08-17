package org.ghotel.ghotel.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ghotel.ghotel.entity.base.BaseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Entity
@Table(name = "room")
@Getter
@Setter
@NamedEntityGraph(
        name = "Room.reservations",
        attributeNodes = @NamedAttributeNode(value = "reservations")
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends BaseEntity {
    @Column(name = "room_number", nullable = false, unique = true)
    private long roomNumber;

    @Column(name = "room_capacity", nullable = false)
    private int roomCapacity;

    @ManyToMany(mappedBy = "rooms")
    @Setter(AccessLevel.NONE)
    private final List<Reservation> reservations = new ArrayList<>();

    public Room(long roomNumber, int roomCapacity) {
        this.roomNumber = roomNumber;
        this.roomCapacity = roomCapacity;
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    public void removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
    }

}
