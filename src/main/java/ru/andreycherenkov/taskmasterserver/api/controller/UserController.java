package ru.andreycherenkov.taskmasterserver.api.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.andreycherenkov.taskmasterserver.api.dto.UserDtoResponse;
import ru.andreycherenkov.taskmasterserver.api.dto.UserLoginDto;
import ru.andreycherenkov.taskmasterserver.api.dto.UserCreateDto;

//todo add @Validation
@Tag(name = "UserController", description = "Endpoints to work with users")
@RequestMapping("api/v1/users")
public interface UserController {

    @GetMapping("/test")
    ResponseEntity<String> test();

    //todo write DTO classes
    @PostMapping("/register")
    ResponseEntity<UserDtoResponse> createUser(@RequestBody UserCreateDto userCreateDto);

    @PostMapping("/login")
    ResponseEntity<UserDtoResponse> login(@RequestBody UserLoginDto userLoginDto);

    @GetMapping("/{userId}")
    ResponseEntity<UserDtoResponse> getUser(@PathVariable String userId);

}
