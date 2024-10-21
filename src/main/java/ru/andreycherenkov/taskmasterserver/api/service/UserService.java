package ru.andreycherenkov.taskmasterserver.api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.andreycherenkov.taskmasterserver.api.dto.UserDtoResponse;
import ru.andreycherenkov.taskmasterserver.api.dto.UserLoginDto;
import ru.andreycherenkov.taskmasterserver.api.dto.UserCreateDto;

public interface UserService {

    ResponseEntity<UserDtoResponse> createUser(@RequestBody UserCreateDto userCreateDto);

    ResponseEntity<UserDtoResponse> login(@RequestBody UserLoginDto userLoginDto);

    ResponseEntity<UserDtoResponse> getUser(@PathVariable String userId);

}
