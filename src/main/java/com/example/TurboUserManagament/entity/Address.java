package com.example.TurboUserManagament.entity;

import com.example.TurboUserManagament.appenum.AddressType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
//@Entity
public class Address {
    private Long id;
    private String city;
    private String street;
    private Double latitude;
    private Double longitude;
    private AddressType addressType;

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
