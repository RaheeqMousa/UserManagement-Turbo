package com.example.TurboUserManagament.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
//@Entity
@Data
@Builder
public class Vehicle {
    private Long id;
    private Driver driver;

    private String model;
    private String type;
    private String color;
    private String plateNumber;
}
