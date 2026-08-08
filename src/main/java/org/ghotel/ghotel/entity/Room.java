package org.ghotel.ghotel.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "room")
@NamedEntityGraph(
        name = "Room.customer",
        includeAllAttributes = false,
        attributeNodes = @NamedAttributeNode("customer")
)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_generator")
    @SequenceGenerator(
            name = "room_generator",
            sequenceName = "room_sequence",
            initialValue = 1,
            allocationSize = 1
    )
    @Column(name = "room_id")
    private Long id;

    @Column(name = "room_capacity", nullable = false)
    private int roomCapacity;

    @Column(name = "taken", nullable = false)
    private boolean taken;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    protected Room() {
    }

    public Room(int roomCapacity, boolean taken, boolean deleted) {
        this.roomCapacity = roomCapacity;
        this.taken = taken;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Room room)) return false;
        return Objects.equals(getId(), room.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
