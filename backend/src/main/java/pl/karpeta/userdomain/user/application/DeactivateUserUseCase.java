package pl.karpeta.userdomain.user.application;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.karpeta.userdomain.user.domain.User;
import pl.karpeta.userdomain.user.domain.UserRepository;
import pl.karpeta.userdomain.user.domain.exception.UserNotFoundException;

@Service
public class DeactivateUserUseCase {
    private final UserRepository userRepository;

    public DeactivateUserUseCase(UserRepository userRepo) {
        this.userRepository = userRepo;
    }

    @Transactional
    public User execute(String email) {
        User foundUser = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        if (foundUser.active()) {
            User deactivatedUser = new User(foundUser.id(), email, foundUser.name(), foundUser.password(), false);
            userRepository.save(deactivatedUser);
            return deactivatedUser;
        } else {
            System.out.println("User " + email + " is inactive yet");
            return foundUser;
        }
    }
}
