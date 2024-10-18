package ru.andreycherenkov.taskmasterserver.impl.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.andreycherenkov.taskmasterserver.api.controller.UserController;
import ru.andreycherenkov.taskmasterserver.api.dto.UserDataResponse;
import ru.andreycherenkov.taskmasterserver.api.dto.UserLoginDto;
import ru.andreycherenkov.taskmasterserver.api.dto.UserRegisterDto;
import ru.andreycherenkov.taskmasterserver.api.service.UserService;

@RestController
@AllArgsConstructor
public class UserControllerImpl implements UserController {

    private UserService userService;

    @Override
    public String test() {
        return "User controller getter test";
    }

    @Override
    public ResponseEntity<UserDataResponse> createUser(UserRegisterDto userRegisterDto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseEntity<UserDataResponse> login(UserLoginDto userLoginDto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseEntity<UserDataResponse> getUser(String userId) {
        throw new UnsupportedOperationException();
    }
}
