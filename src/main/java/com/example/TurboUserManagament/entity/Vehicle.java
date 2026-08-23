package com.example.TurboUserManagament.entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
//@Entity
@Data
public class Vehicle {
    private Long id;
    private User user;
    private String model;
    private String type;

    private Driver driver;
}
