package pl.karpeta.userdomain.user.api;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.karpeta.userdomain.user.domain.User;
import pl.karpeta.userdomain.user.application.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private final RegisterUserUseCase registerUserUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final ActivateUserUseCase activateUserUseCase;
    private final DeactivateUserUseCase deactivateUserUseCase;
    private final DeleteAllUsersUseCase deleteAllUsersUseCase;

    public UserController(
        RegisterUserUseCase registerUser,
        GetAllUsersUseCase getAll,
        ActivateUserUseCase activateUser,
        DeactivateUserUseCase deactivateUserUseCase,
        DeleteAllUsersUseCase deleteAllUsersUseCase
    ) {
        this.registerUserUseCase = registerUser;
        this.getAllUsersUseCase = getAll;
        this.activateUserUseCase = activateUser;
        this.deactivateUserUseCase = deactivateUserUseCase;
        this.deleteAllUsersUseCase = deleteAllUsersUseCase;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto register(@Valid @RequestBody RegisterUserRequest request) {
        User user = registerUserUseCase.register(
                new RegisterUserCommand(
                        request.email(),
                        request.name(),
                        request.password()
                )
        );

        return UserDto.from(user);
    }

    @GetMapping("/getAll")
    public List<UserDto> getAllUsers() {
        return getAllUsersUseCase.getAllUsers().stream().map(UserDto::from).collect(Collectors.toList());
    }

    @PostMapping("/{email}/activate")
    public UserDto activateUser(@PathVariable  String email) {
        return UserDto.from(activateUserUseCase.activate(email));
    }

    @PostMapping("/{email}/deactivate")
    public UserDto deactivateUser(@PathVariable  String email) {
        return UserDto.from(deactivateUserUseCase.execute(email));
    }

    @PostMapping("/deleteAll")
    public void deleteAllUsers() {
        deleteAllUsersUseCase.execute();
    }
}
