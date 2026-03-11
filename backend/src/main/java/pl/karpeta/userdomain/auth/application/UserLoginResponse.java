package pl.karpeta.userdomain.auth.application;

public record UserLoginResponse(
        Long id,
        String name,
        String email
) {}
