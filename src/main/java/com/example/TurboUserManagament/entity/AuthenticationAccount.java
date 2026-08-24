package com.example.TurboUserManagament.entity;

import com.example.TurboUserManagament.appenum.AccountStatus;
import com.example.TurboUserManagament.record.Password;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
//@Entity
public class AuthenticationAccount {
    private Long id;
    private User user;
    private Password password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private AccountStatus status;

    private PhoneVerification phoneVerification;

    @Override
    public String toString() {
        return "AuthenticationAccount{" +
                "id=" + id +
                ", user ID=" + user.getId() +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", status=" + status +
                '}';
    }
}
