package pl.karpeta.userdomain.user.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import pl.karpeta.userdomain.user.domain.User;
import pl.karpeta.userdomain.user.domain.UserRepository;

import java.util.*;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> users = new HashMap<>();

    @Override
    public User save(User user) {
        return this.users.put(user.email(), user);
    }

    @Override
    public List<User> findAll() {
        return users.values().stream().toList();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(users.get(email));
    }

    @Override
    public List<User> findAllActive() {
        return users.values().stream().filter(User::active).toList();
    }

    @Override
    public boolean existsByEmail(String email) {
        return users.containsKey(email);
    }

    @Override
    public long countActive() {
        return users.values().stream().filter(User::active).count();
    }

    @Override
    public void deleteAll() {
        users.clear();
    }
}
