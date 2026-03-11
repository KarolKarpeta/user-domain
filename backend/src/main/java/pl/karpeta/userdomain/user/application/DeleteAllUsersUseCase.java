package pl.karpeta.userdomain.user.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.karpeta.userdomain.user.domain.UserRepository;

@Service
public class DeleteAllUsersUseCase {
    private final UserRepository userRepository;

    public DeleteAllUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute() {
        userRepository.deleteAll();
    }
}
