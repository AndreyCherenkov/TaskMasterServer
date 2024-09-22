package ru.andreycherenkov.taskmasterserver.api.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.andreycherenkov.taskmasterserver.api.dto.UserDataResponse;
import ru.andreycherenkov.taskmasterserver.api.dto.UserLoginDto;
import ru.andreycherenkov.taskmasterserver.api.dto.UserRegisterDto;

//todo add @Validation
@Tag(name = "UserController", description = "Endpoints to work with users")
@RequestMapping("api/v1/users")
public interface UserController {

    @GetMapping("/test")
    String test();

    //todo write DTO classes
    @PostMapping("/register")
    ResponseEntity<Object> createUser(@RequestBody UserRegisterDto userRegisterDto);

    @PostMapping("/login")
    ResponseEntity<Object> login(@RequestBody UserLoginDto userLoginDto);

    @GetMapping("/{userId}")
    ResponseEntity<UserDataResponse> getUser(@PathVariable String userId);




}
