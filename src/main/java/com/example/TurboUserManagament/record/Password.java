package com.example.TurboUserManagament.record;

import jakarta.persistence.Embeddable;

@Embeddable
public record Password(String value) {
    public Password {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Password can't be empty");
        }
    }

    public static void validate(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException(
                    "Password must be at least 8 characters"
            );
        }

        if (!password.matches(".*\\d.*")
                || !password.matches(".*[a-zA-Z].*")
                || !password.matches(".*[^a-zA-Z0-9].*")) {
            throw new IllegalArgumentException(
                    "Password must contain letters, digits and special characters"
            );
        }
    }

}
