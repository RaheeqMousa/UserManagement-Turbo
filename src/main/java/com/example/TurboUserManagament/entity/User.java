package com.example.TurboUserManagament.entity;

import com.example.TurboUserManagament.appenum.UserRole;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.TurboUserManagament.converter.PhoneNumberConverter;
import com.example.TurboUserManagament.record.PhoneNumber;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Convert(converter = PhoneNumberConverter.class)
    private PhoneNumber phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column(name="created_at")
    private LocalDateTime createdAt;
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private AuthenticationAccount authenticationAccount;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PhoneVerification> phoneVerifications = new ArrayList<>();

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
