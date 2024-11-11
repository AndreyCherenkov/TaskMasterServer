package ru.andreycherenkov.taskmasterserver.impl.service;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.andreycherenkov.taskmasterserver.api.dto.UserDtoResponse;
import ru.andreycherenkov.taskmasterserver.api.dto.UserLoginDto;
import ru.andreycherenkov.taskmasterserver.api.dto.UserCreateDto;
import ru.andreycherenkov.taskmasterserver.api.service.UserService;
import ru.andreycherenkov.taskmasterserver.db.entity.ApplicationUser;
import ru.andreycherenkov.taskmasterserver.db.repository.UserRepository;
import ru.andreycherenkov.taskmasterserver.impl.exception.NotFoundException;
import ru.andreycherenkov.taskmasterserver.impl.exception.PasswordConfirmationException;
import ru.andreycherenkov.taskmasterserver.impl.mapper.UserMapper;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<UserDtoResponse> createUser(UserCreateDto userCreateDto) {
        validatePassword(userCreateDto);

        userCreateDto.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        ApplicationUser user = userRepository.save(userMapper.toUser(userCreateDto));
        UserDtoResponse userDtoResponse = userMapper.applicationUserToUserDtoResponse(user);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userDtoResponse);
    }

    private void validatePassword(UserCreateDto userCreateDto) {
        if (!userCreateDto.getPassword().equals(userCreateDto.getConfirmedPassword())) {
            throw new PasswordConfirmationException("Passwords are not equal");
        }
    }

    @Override
    public ResponseEntity<UserDtoResponse> login(UserLoginDto userLoginDto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseEntity<UserDtoResponse> getUser(String userId) {
        ApplicationUser user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new NotFoundException("User not found"));
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userMapper.applicationUserToUserDtoResponse(user));
    }
}
