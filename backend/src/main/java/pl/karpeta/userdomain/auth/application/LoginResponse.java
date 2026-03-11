package pl.karpeta.userdomain.auth.application;

public record LoginResponse(
        String token,
        UserLoginResponse userLoginResponse
) {}
