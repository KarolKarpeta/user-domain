package pl.karpeta.userdomain.auth.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.karpeta.userdomain.auth.application.LoginRequest;
import pl.karpeta.userdomain.auth.application.LoginResponse;
import pl.karpeta.userdomain.auth.application.LoginUserUseCase;
import pl.karpeta.userdomain.user.domain.User;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final LoginUserUseCase loginUserUseCase;

    public AuthController(LoginUserUseCase loginUserUseCase) {
        this.loginUserUseCase = loginUserUseCase;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return loginUserUseCase.login(request);
    }
}
