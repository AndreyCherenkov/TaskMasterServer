package ru.andreycherenkov.taskmasterserver.api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.andreycherenkov.taskmasterserver.api.dto.AuthResponse;
import ru.andreycherenkov.taskmasterserver.api.dto.UserDtoResponse;
import ru.andreycherenkov.taskmasterserver.api.dto.UserLoginDto;
import ru.andreycherenkov.taskmasterserver.api.dto.UserCreateDto;

public interface UserService {

    ResponseEntity<UserDtoResponse> createUser(@RequestBody UserCreateDto userCreateDto);

    ResponseEntity<AuthResponse> login(@RequestBody UserLoginDto userLoginDto);

    ResponseEntity<Void> logout();

    ResponseEntity<UserDtoResponse> getUser(@RequestHeader("User-Id") String userId);

}
