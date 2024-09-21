package ru.andreycherenkov.taskmasterserver.impl;

import org.springframework.web.bind.annotation.RestController;
import ru.andreycherenkov.taskmasterserver.api.UserController;

@RestController
public class UserControllerImpl implements UserController {

    @Override
    public String test() {
        return "User controller getter test";
    }
}
