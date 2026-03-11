package pl.karpeta.userdomain.user.application;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserCommand(
        @NotBlank
        @Email
        String email,
        String name,
        String password
) {
}
