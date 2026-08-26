package com.example.TurboUserManagament.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Column(name="birth_date")
    private LocalDate birthDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "customer_addresses",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private List<Address> addresses;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selected_address_id")
    private Address selectedAddress;

    @Column(name="current_latitude")
    private Double currentLatitude;

    @Column(name="current_longitude")
    private Double currentLongitude;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", birthDate=" + birthDate +
                '}';
    }
}
