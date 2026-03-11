package pl.karpeta.userdomain.user.domain;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    List<User> findAllActive();

    boolean existsByEmail(String email);

    long countActive();

    void deleteAll();
}
