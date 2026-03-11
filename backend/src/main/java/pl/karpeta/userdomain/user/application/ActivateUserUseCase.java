package pl.karpeta.userdomain.user.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.karpeta.userdomain.user.domain.User;
import pl.karpeta.userdomain.user.domain.UserRepository;
import pl.karpeta.userdomain.user.domain.exception.UserNotFoundException;

@Service
public class ActivateUserUseCase {
    UserRepository userRepository;

    public ActivateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User activate(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        if (!user.active()) {
            User newUser = new User(user.id(), user.email(), user.name(), user.password(), true);
            userRepository.save((newUser));

            return newUser;
        } else {
            System.out.println("User " + email + " is active yet");
            return user;
        }
    }
}
