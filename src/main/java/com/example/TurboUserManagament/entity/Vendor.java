package com.example.TurboUserManagament.entity;

import com.example.TurboUserManagament.appenum.VendorType;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
//@Entity
public class Vendor{
    private Long id;
    private User user;

    private String identityNumber;
    private double averageReview;
    private String businessName;
    private VendorType vendorType;

    private Address businessAddress;

    private LocalDate createdAt;
    private LocalDate updatedAt;
}
