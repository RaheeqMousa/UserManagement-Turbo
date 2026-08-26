package com.example.TurboUserManagament.entity;

import com.example.TurboUserManagament.appenum.AccountStatus;
import com.example.TurboUserManagament.converter.PasswordConverter;
import com.example.TurboUserManagament.record.Password;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AuthenticationAccount {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Convert(converter = PasswordConverter.class)
    private Password password;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Override
    public String toString() {
        return "AuthenticationAccount{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", status=" + status +
                '}';
    }
}
