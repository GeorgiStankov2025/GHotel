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

    private Room(Room.Builder builder) {
        super(builder);
        this.roomNumber = builder.roomNumber;
        this.roomCapacity = builder.roomCapacity;
    }

    public static Room.Builder builder() {
        return new Room.Builder();
    }

    public static class Builder extends BaseEntity.Builder<Room, Room.Builder> {
        private long roomNumber = 0L;
        private int roomCapacity = 0;

        public Room.Builder roomNumber(long roomNumber) {
            this.roomNumber = roomNumber;
            return self();
        }

        public Room.Builder roomCapacity(int roomCapacity) {
            this.roomCapacity = roomCapacity;
            return self();
        }

        @Override
        public Room build() {
            return new Room(this);
        }
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
