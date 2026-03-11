package pl.karpeta.userdomain.user.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email address")
        String email,
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Password is required")
        String password
) {}
