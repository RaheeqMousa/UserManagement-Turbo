package com.example.TurboUserManagament.entity;

import com.example.TurboUserManagament.appenum.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
//@Entity
public class AuthenticationAccount {
    private Long id;
    private User user;
    private String hashedPassword;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private AccountStatus isActive;

    @Override
    public String toString() {
        return "AuthenticationAccount{" +
                "id=" + id +
                ", user ID=" + user.getId() +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isActive=" + isActive +
                '}';
    }
}
