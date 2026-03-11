package pl.karpeta.userdomain.user.infrastructure.persistence;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import pl.karpeta.userdomain.user.domain.User;
import pl.karpeta.userdomain.user.domain.UserRepository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
class UserJpaAdapter implements UserRepository {

    private final UserJpaRepository jpaRepo;
    private final UserEntityMapper mapper;

    public UserJpaAdapter(UserJpaRepository repo, UserEntityMapper entityMapper) {
        this.jpaRepo = repo;
        this.mapper = entityMapper;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepo.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity saved = jpaRepo.save(mapper.toEntity(user));

        return mapper.toDomain(saved);
    }

    @Override
    public List<User> findAll() {
        return jpaRepo.findAll()
                .stream()
                .map(mapper::toDomain)
//                .peek(System.out::println) - jak .tap()
                .toList();
    }

    @Override
    public List<User> findAllActive() {
        return jpaRepo.findAll()
                .stream()
                .filter(UserEntity::isActive)
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepo.findByEmail(email).isPresent();
    }

    @Override
    public long countActive() {
        return jpaRepo.findAll()
                .stream()
                .filter(UserEntity::isActive)
                .count();
    }

    @Override
    public void deleteAll() {
        jpaRepo.deleteAll();
    }
}
