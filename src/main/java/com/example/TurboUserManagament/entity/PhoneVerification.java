package com.example.TurboUserManagament.entity;

import com.example.TurboUserManagament.appenum.PhoneVerificationStatus;
import lombok.Data;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
//@Entity
public class PhoneVerification {
    private Long id;
    private User user;
    private String hashedOTP;
    private LocalDateTime expiryDate;
    private LocalDateTime createdAt;
    private PhoneVerificationStatus status;

    @Override
    public String toString() {
        return "PhoneVerification{" +
                "id=" + id +
                ", user ID=" + user.getId() +
                ", hashedOTP='" + hashedOTP + '\'' +
                ", expiryDate=" + expiryDate +
                ", createdAt=" + createdAt +
                ", status=" + status +
                '}';
    }
}
