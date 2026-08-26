package com.example.TurboUserManagament.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CallCenterAgent {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Override
    public String toString() {
        return "CallCenterAgent{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }
}
