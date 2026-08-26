package com.example.TurboUserManagament.entity;

import com.example.TurboUserManagament.appenum.VendorType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vendor{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name="identity_number")
    private String identityNumber;

    @Column(name="average_review")
    private double averageReview;

    @Column(name="business_name")
    private String businessName;

    @Column(name="vendor_type")
    private VendorType vendorType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_address_id", nullable = false)
    private Address businessAddress;

    @Column(name = "opens_at")
    private LocalTime opensAt;

    @Column(name = "closes_at")
    private LocalTime closesAt;

    @Column(name="created_at")
    private LocalDate createdAt;

    @Column(name="updated_at")
    private LocalDate updatedAt;

    @Override
    public String toString() {
        return "Vendor{" +
                "id=" + id +
                ", identityNumber='" + identityNumber + '\'' +
                ", averageReview=" + averageReview +
                ", businessName='" + businessName + '\'' +
                ", vendorType=" + vendorType +
                ", businessAddress=" + businessAddress +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
