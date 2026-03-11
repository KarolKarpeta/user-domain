package pl.karpeta.userdomain.user.application;
import org.springframework.transaction.annotation.Transactional;
import pl.karpeta.userdomain.user.domain.User;
import pl.karpeta.userdomain.user.domain.UserRepository;

import java.util.List;

public class GetActiveUsersUseCase {
    private final UserRepository userRepository;

    public GetActiveUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional(readOnly = true)
    public List<User> execute() {
        return userRepository.findAllActive();
    }
}
