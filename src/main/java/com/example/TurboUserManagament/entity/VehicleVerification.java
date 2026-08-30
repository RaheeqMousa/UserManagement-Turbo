package com.example.TurboUserManagament.entity;

import com.example.TurboUserManagament.appenum.VehicleVerificationStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class VehicleVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(name = "file_url")
    private String fileURL;

    @Column(name="uploaded_at")
    private LocalDateTime uploadedAt;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    @Enumerated(EnumType.STRING)
    @Column(name="vehicle_verification_status")
    private VehicleVerificationStatus vehicleVerificationStatus;

    @Column(name="rejection_reason")
    private String rejectionReason;

    @Override
    public String toString() {
        return "VehicleVerification{" +
                "id=" + id +
                ", fileURL='" + fileURL + '\'' +
                ", uploadedAt=" + uploadedAt +
                ", verifiedAt=" + verifiedAt +
                ", vehicleVerificationStatus=" + vehicleVerificationStatus +
                ", rejectionReason='" + rejectionReason + '\'' +
                '}';
    }
}
