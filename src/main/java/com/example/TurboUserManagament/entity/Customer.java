package com.example.TurboUserManagament.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
//@Entity
public class Customer {
    private Long id;
    private User user;

    private LocalDate birthDate;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", user id=" + user.getId() +
                ", birthDate=" + birthDate +
                '}';
    }
}
