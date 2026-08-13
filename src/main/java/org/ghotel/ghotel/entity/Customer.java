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
@Table(name = "customer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NamedEntityGraph(
        name = "Customer.reservations",
        attributeNodes = @NamedAttributeNode("reservations")
)
@Getter
@Setter
public class Customer extends BaseEntity {
    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @Setter(AccessLevel.NONE)
    private List<Reservation> reservations = new ArrayList<>();

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
