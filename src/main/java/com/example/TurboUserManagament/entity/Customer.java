package com.example.TurboUserManagament.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
//@Entity
public class Customer {
    private Long id;
    private User user;

    private LocalDate birthDate;

    private List<Address> addresses;
    private Address selectedAddress;

    private Double currentLatitude;
    private Double currentLongitude;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", user id=" + user.getId() +
                ", birthDate=" + birthDate +
                '}';
    }
}
