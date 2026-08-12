package org.ghotel.ghotel.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ghotel.ghotel.entity.base.BaseEntity;


@Entity
@Table(name = "room")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NamedEntityGraph(
        name = "Room.customer",
        includeAllAttributes = false,
        attributeNodes = @NamedAttributeNode("customer")
)
public class Room extends BaseEntity {

    @Column(name = "room_capacity", nullable = false)
    private int roomCapacity;

    @Column(name = "taken", nullable = false)
    private boolean taken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Room(int roomCapacity, boolean taken) {
        this.roomCapacity = roomCapacity;
        this.taken = taken;
    }
}
