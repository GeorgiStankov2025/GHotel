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

    private Customer(Builder builder) {
        super(builder);
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
    }

    public static Customer.Builder builder() {
        return new Customer.Builder();
    }

    public static class Builder extends BaseEntity.Builder<Customer, Builder> {
        private String firstName = "defaultFirstName";
        private String lastName = "defaultLastName";

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return self();
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return self();
        }

        @Override
        public Customer build() {
            return new Customer(this);
        }
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
