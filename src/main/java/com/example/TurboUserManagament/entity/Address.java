package com.example.TurboUserManagament.entity;

import com.example.TurboUserManagament.appenum.AddressStatus;
import com.example.TurboUserManagament.appenum.AddressType;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String street;
    private Double latitude;
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column(name="address_type")
    private AddressType addressType;

    private AddressStatus status;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
