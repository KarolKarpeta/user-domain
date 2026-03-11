package pl.karpeta.userdomain.user.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.karpeta.userdomain.user.domain.User;
import pl.karpeta.userdomain.user.domain.UserRepository;

import java.util.List;

@Service
public class GetAllUsersUseCase {
    private final UserRepository userRepository;

    public GetAllUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
