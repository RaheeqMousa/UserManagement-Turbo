package com.example.TurboUserManagament.entity;

import com.example.TurboUserManagament.appenum.VendorType;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
//@Entity
public class Vendor{
    private Long id;
    private User user;

    private double averageReview;
    private String placeName;
    private VendorType vendorType;

    private Address address;

    private LocalDate createdAt;
    private LocalDate updatedAt;
}
