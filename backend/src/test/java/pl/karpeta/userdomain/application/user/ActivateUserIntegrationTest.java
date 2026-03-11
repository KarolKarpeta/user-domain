package pl.karpeta.userdomain.application.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.karpeta.userdomain.user.infrastructure.persistence.UserEntity;
import pl.karpeta.userdomain.user.infrastructure.persistence.UserJpaRepository;
import pl.karpeta.userdomain.user.application.ActivateUserUseCase;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ActivateUserIntegrationTest {

    @Autowired
    private ActivateUserUseCase activateUserUseCase;

    @Autowired
    private UserJpaRepository repository;

    @Test
    void shouldActivateUser() {
        UserEntity entity = new UserEntity();
        entity.setEmail("testtt@aa.pl");
        entity.setName("Karol");
        entity.setActive(false);

        repository.save(entity);
        repository.flush();

        activateUserUseCase.activate(entity.email);

        UserEntity updated = repository.findByEmail("testtt@aa.pl").orElseThrow();

        assertThat(updated.isActive()).isTrue();
    }
}
