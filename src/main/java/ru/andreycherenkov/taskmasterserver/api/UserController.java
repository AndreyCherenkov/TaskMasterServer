package ru.andreycherenkov.taskmasterserver.api;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//todo add @Validation
@Tag(name = "UserController", description = "Endpoints to work with users")
@RequestMapping("api/v1/users")
public interface UserController {

    @GetMapping("/test")
    String test();

}
