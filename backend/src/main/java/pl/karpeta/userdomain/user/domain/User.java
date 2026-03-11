package pl.karpeta.userdomain.user.domain;

public record User(
        Long id,
        String email,
        String name,
        HashedPassword password,
        boolean active
) {}
