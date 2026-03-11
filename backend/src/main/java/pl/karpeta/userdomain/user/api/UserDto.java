package pl.karpeta.userdomain.user.api;

import pl.karpeta.userdomain.user.domain.User;

public record UserDto(
        String id,
        String email,
        String name,
        boolean active
) {
    public static UserDto from(User user) {
        return new UserDto(
                user.id().toString(),
                user.email(),
                user.name(),
                user.active()
        );
    }
}
