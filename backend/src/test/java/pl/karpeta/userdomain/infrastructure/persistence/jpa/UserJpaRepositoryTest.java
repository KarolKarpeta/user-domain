package pl.karpeta.userdomain.infrastructure.persistence.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import pl.karpeta.userdomain.user.infrastructure.persistence.UserEntity;
import pl.karpeta.userdomain.user.infrastructure.persistence.UserJpaRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserJpaRepositoryTest {

    @Autowired
    private UserJpaRepository repository;

    @Test
    void shouldSaveAndFindUserByEmail() {
        UserEntity entity = new UserEntity();
        entity.setEmail("testowy@aa.pl");
        entity.setName("Karol");
        entity.setActive(false);

        repository.save(entity);

        Optional<UserEntity> foundUser = repository.findByEmail("testowy@aa.pl");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().email).isEqualTo("testowy@aa.pl");
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNotUnique() {
        UserEntity first = new UserEntity();
        first.setEmail("testowy2@aa.pl");
        first.setName("A");
        first.setActive(true);

        UserEntity second = new UserEntity();
        second.setEmail("testowy2@aa.pl");
        second.setName("B");
        second.setActive(false);

        repository.save(first);
        repository.save(second);

        assertThatThrownBy(() -> repository.flush())
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void shouldUpdateUser() {
        UserEntity user = new UserEntity();
        user.setEmail("a@a.pl");
        user.setName("Karol");
        user.setActive(false);

        repository.save(user);
        repository.flush();

        user.setActive(true);
        repository.flush();

        UserEntity found = repository.findByEmail("a@a.pl").orElseThrow();

        assertThat(found.isActive()).isTrue();
    }
}
