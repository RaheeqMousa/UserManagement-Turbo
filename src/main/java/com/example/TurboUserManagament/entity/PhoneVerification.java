package com.example.TurboUserManagament.entity;

import com.example.TurboUserManagament.appenum.PhoneVerificationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PhoneVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String otp;

    @Column(name="expiry_date")
    private LocalDateTime expiryDate;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="verified_at")
    private LocalDateTime verifiedAt;

    @Enumerated(EnumType.STRING)
    private PhoneVerificationStatus status;

    @Override
    public String toString() {
        return "PhoneVerification{" +
                "id=" + id +
                ", OTP='" + otp + '\'' +
                ", expiryDate=" + expiryDate +
                ", createdAt=" + createdAt +
                ", status=" + status +
                '}';
    }
}
