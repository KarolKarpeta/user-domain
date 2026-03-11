package pl.karpeta.userdomain.auth.application;

public record LoginRequest(
        String email,
        String password
) {
}
