package com.example.TurboUserManagament.entity;

import com.example.TurboUserManagament.appenum.UserRole;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
//@Entity
public abstract class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private PhoneVerification phoneVerification;
    private AuthenticationAccount authenticationAccount;

    private List<Address> addresses;

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
                ", phoneVerification=" + phoneVerification +
                ", authenticationAccount=" + authenticationAccount +
                ", addresses=" + addresses +
                '}';
    }
}
