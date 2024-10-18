package ru.andreycherenkov.taskmasterserver.impl.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.andreycherenkov.taskmasterserver.api.dto.UserDataResponse;
import ru.andreycherenkov.taskmasterserver.api.dto.UserLoginDto;
import ru.andreycherenkov.taskmasterserver.api.dto.UserRegisterDto;
import ru.andreycherenkov.taskmasterserver.api.service.UserService;

@Service
public class UserServiceImpl implements UserService {

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
