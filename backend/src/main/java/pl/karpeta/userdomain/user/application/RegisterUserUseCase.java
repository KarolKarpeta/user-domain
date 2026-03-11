package pl.karpeta.userdomain.user.application;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.karpeta.userdomain.user.domain.HashedPassword;
import pl.karpeta.userdomain.user.domain.User;
import pl.karpeta.userdomain.user.domain.UserRepository;
import pl.karpeta.userdomain.user.domain.exception.InvalidEmailAddressException;
import pl.karpeta.userdomain.user.domain.exception.UserAlreadyExistsException;

import java.util.regex.Pattern;

@Service
public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserUseCase(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User register(RegisterUserCommand registerUserCommand) {
        if(!isValidEmail(registerUserCommand.email())) {
            throw new InvalidEmailAddressException(registerUserCommand.email());
        }

        if (userRepository.findByEmail(registerUserCommand.email()).isPresent()) {
            throw new UserAlreadyExistsException(registerUserCommand.email());
        }

        HashedPassword hashedPassword = new HashedPassword(passwordEncoder.encode(registerUserCommand.password()));

        User user = new User(
            null,
            registerUserCommand.email(),
            registerUserCommand.name(),
            hashedPassword,
            false
        );

        return userRepository.save(user);
    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    private boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
}
