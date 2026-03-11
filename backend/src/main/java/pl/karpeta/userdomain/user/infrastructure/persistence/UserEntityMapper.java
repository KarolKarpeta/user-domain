package pl.karpeta.userdomain.user.infrastructure.persistence;

import org.springframework.stereotype.Component;
import pl.karpeta.userdomain.user.domain.HashedPassword;
import pl.karpeta.userdomain.user.domain.User;

@Component
public class UserEntityMapper {

    public User toDomain(UserEntity entity) {
        return new User(
                entity.id,
                entity.email,
                entity.name,
                new HashedPassword(entity.password),
                entity.active
        );
    }

    public UserEntity toEntity(User domain) {
        return new UserEntity(domain.id(), domain.email(), domain.name(), domain.password().value(), domain.active());
    }
}
