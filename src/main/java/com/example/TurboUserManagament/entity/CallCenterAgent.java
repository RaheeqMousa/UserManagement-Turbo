package com.example.TurboUserManagament.entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
//@Entity
public class CallCenterAgent {
    private Long id;
    private User user;

    @Override
    public String toString() {
        return "CallCenterAgent{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }
}
