package com.example.TurboUserManagament.entity;

import com.example.TurboUserManagament.appenum.PhoneVerificationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
//@Entity
public class PhoneVerification {
    private Long id;
    private User user;
    private String otp;
    private LocalDateTime expiryDate;
    private LocalDateTime createdAt;
    private LocalDateTime verifiedAt;
    private PhoneVerificationStatus status;

    @Override
    public String toString() {
        return "PhoneVerification{" +
                "id=" + id +
                ", user ID=" + user.getId() +
                ", OTP='" + otp + '\'' +
                ", expiryDate=" + expiryDate +
                ", createdAt=" + createdAt +
                ", status=" + status +
                '}';
    }
}
