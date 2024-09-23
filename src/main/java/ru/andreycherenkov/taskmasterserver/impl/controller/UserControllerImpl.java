package ru.andreycherenkov.taskmasterserver.impl.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.andreycherenkov.taskmasterserver.api.controller.UserController;
import ru.andreycherenkov.taskmasterserver.api.dto.UserDataResponse;
import ru.andreycherenkov.taskmasterserver.api.dto.UserLoginDto;
import ru.andreycherenkov.taskmasterserver.api.dto.UserRegisterDto;

@RestController
public class UserControllerImpl implements UserController {

    @Override
    public String test() {
        return "User controller getter test";
    }

    @Override
    public ResponseEntity<Object> createUser(UserRegisterDto userRegisterDto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseEntity<Object> login(UserLoginDto userLoginDto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseEntity<UserDataResponse> getUser(String userId) {
        throw new UnsupportedOperationException();
    }
}
