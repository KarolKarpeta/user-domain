package pl.karpeta.userdomain.application.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.karpeta.userdomain.user.infrastructure.persistence.UserEntity;
import pl.karpeta.userdomain.user.infrastructure.persistence.UserJpaRepository;
import pl.karpeta.userdomain.user.application.ActivateUserUseCase;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class ActivateUserContainerTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15")
                .withDatabaseName("testdb")
                .withUsername("postgres")
                .withPassword("postgres");

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    ActivateUserUseCase activateUserUseCase;

    @Autowired
    UserJpaRepository repository;

    @Test
    void shouldActivateUser() {
        UserEntity entity = new UserEntity();
        entity.setEmail("test@test.pl");
        entity.setName("Karol");
        entity.setActive(false);

        repository.save(entity);
        repository.flush();

        activateUserUseCase.activate(entity.email);
        repository.flush();

        UserEntity foundUser = repository.findByEmail(entity.email).orElseThrow();

        assertThat(foundUser.isActive()).isTrue();
    }
}
