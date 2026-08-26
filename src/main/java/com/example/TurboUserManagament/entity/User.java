package com.example.TurboUserManagament.entity;

import com.example.TurboUserManagament.appenum.UserRole;
import java.time.LocalDateTime;
import java.util.List;

import com.example.TurboUserManagament.record.PhoneNumber;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
//@Entity
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private PhoneNumber phoneNumber;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private AuthenticationAccount authenticationAccount;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role=" + role +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", authenticationAccount=" + authenticationAccount +
                '}';
    }
}
