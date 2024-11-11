package ru.andreycherenkov.taskmasterserver.impl.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.andreycherenkov.taskmasterserver.api.controller.UserController;
import ru.andreycherenkov.taskmasterserver.api.dto.UserDtoResponse;
import ru.andreycherenkov.taskmasterserver.api.dto.UserLoginDto;
import ru.andreycherenkov.taskmasterserver.api.dto.UserCreateDto;
import ru.andreycherenkov.taskmasterserver.api.service.UserService;

@RestController
@AllArgsConstructor
public class UserControllerImpl implements UserController {

    private UserService userService;

    @Override
    public ResponseEntity<String> test() {
        String responseBody = "{\"message\": \"Hello, Android\"}"; // Правильный формат JSON
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseBody);
    }

    @Override
    public ResponseEntity<UserDtoResponse> createUser(UserCreateDto userCreateDto) {
        return userService.createUser(userCreateDto);
    }

    @Override
    public ResponseEntity<UserDtoResponse> login(UserLoginDto userLoginDto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseEntity<UserDtoResponse> getUser(String userId) {
        return userService.getUser(userId);
    }
}
