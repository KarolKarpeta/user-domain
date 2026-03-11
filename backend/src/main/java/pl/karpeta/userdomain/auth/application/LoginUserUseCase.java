package pl.karpeta.userdomain.auth.application;

import io.jsonwebtoken.Jwts;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.karpeta.userdomain.auth.infrastructure.jwt.JwtService;
import pl.karpeta.userdomain.user.domain.User;
import pl.karpeta.userdomain.user.domain.UserRepository;

@Service
public class LoginUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginUserUseCase(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email()).orElseThrow(InvalidCredentialsException::new);

        boolean passwordValid = passwordEncoder.matches(request.password(), user.password().value());

        if (!passwordValid) {
            throw new InvalidCredentialsException();
        }

        String token = jwtService.generateToken(user.email());

        UserLoginResponse userLoginResponse = new UserLoginResponse(user.id(), user.name(), user.email());

        return new LoginResponse(token, userLoginResponse);
    }
}
