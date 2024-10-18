package ru.andreycherenkov.taskmasterserver.api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.andreycherenkov.taskmasterserver.api.dto.UserDataResponse;
import ru.andreycherenkov.taskmasterserver.api.dto.UserLoginDto;
import ru.andreycherenkov.taskmasterserver.api.dto.UserRegisterDto;

public interface UserService {

    ResponseEntity<UserDataResponse> createUser(@RequestBody UserRegisterDto userRegisterDto);

    ResponseEntity<UserDataResponse> login(@RequestBody UserLoginDto userLoginDto);

    ResponseEntity<UserDataResponse> getUser(@PathVariable String userId);

}
