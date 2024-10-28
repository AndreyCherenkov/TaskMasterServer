package ru.andreycherenkov.taskmasterserver.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.andreycherenkov.taskmasterserver.api.dto.UserCreateDto;
import ru.andreycherenkov.taskmasterserver.api.dto.UserDtoResponse;
import ru.andreycherenkov.taskmasterserver.db.entity.ApplicationUser;
import ru.andreycherenkov.taskmasterserver.db.repository.UserRepository;
import ru.andreycherenkov.taskmasterserver.impl.exception.NotFoundException;
import ru.andreycherenkov.taskmasterserver.impl.exception.PasswordConfirmationException;
import ru.andreycherenkov.taskmasterserver.impl.mapper.UserMapper;
import ru.andreycherenkov.taskmasterserver.impl.service.UserServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password123";
    private static final String CONFIRMED_PASSWORD = "password123";
    private static final String DIFFERENT_PASSWORD = "differentPassword";
    private static final String USER_ID = UUID.randomUUID().toString();

    private static final UserCreateDto userCreateDto = UserCreateDto.builder()
            .username(USERNAME)
            .password(PASSWORD)
            .confirmedPassword(CONFIRMED_PASSWORD)
            .build();

    private static final ApplicationUser applicationUser = new ApplicationUser();
    private static final UserDtoResponse userDtoResponse = new UserDtoResponse();

    @Test
    public void createUserShouldReturnOkWhenPasswordsMatch() {
        when(userMapper.toUser(userCreateDto)).thenReturn(applicationUser);
        when(userRepository.save(applicationUser)).thenReturn(applicationUser);
        when(userMapper.applicationUserToUserDtoResponse(applicationUser)).thenReturn(userDtoResponse);

        ResponseEntity<UserDtoResponse> response = userService.createUser(userCreateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userDtoResponse, response.getBody());

        verify(userMapper).toUser(userCreateDto);
        verify(userRepository).save(applicationUser);
        verify(userMapper).applicationUserToUserDtoResponse(applicationUser);
    }

    @Test
    public void createUserShouldThrowExceptionWhenPasswordsDoNotMatch() {
        UserCreateDto invalidUserCreateDto = UserCreateDto.builder()
                .username("testUser")
                .password(PASSWORD)
                .confirmedPassword(DIFFERENT_PASSWORD)
                .build();

        Exception exception = assertThrows(PasswordConfirmationException.class, () -> {
            userService.createUser(invalidUserCreateDto);
        });

        assertEquals("Passwords are not equal", exception.getMessage());

        verify(userRepository, never()).save(any());
        verify(userMapper, never()).applicationUserToUserDtoResponse(any());
    }

    @Test
    public void getUserShouldReturnUserWhenExists() {
        when(userRepository.findById(UUID.fromString(USER_ID))).thenReturn(Optional.of(applicationUser));
        when(userMapper.applicationUserToUserDtoResponse(applicationUser)).thenReturn(userDtoResponse);

        ResponseEntity<UserDtoResponse> response = userService.getUser(USER_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userDtoResponse, response.getBody());

        verify(userRepository).findById(UUID.fromString(USER_ID));
        verify(userMapper).applicationUserToUserDtoResponse(applicationUser);
    }

    @Test
    public void getUserShouldThrowNotFoundExceptionWhenUserDoesNotExist() {
        when(userRepository.findById(UUID.fromString(USER_ID))).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            userService.getUser(USER_ID);
        });

        assertEquals("User not found", exception.getMessage());

        verify(userRepository).findById(UUID.fromString(USER_ID));
        verify(userMapper, never()).applicationUserToUserDtoResponse(any());
    }
}

