package ru.andreycherenkov.taskmasterserver.impl.service;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.andreycherenkov.taskmasterserver.api.dto.*;
import ru.andreycherenkov.taskmasterserver.api.service.UserService;
import ru.andreycherenkov.taskmasterserver.db.entity.ApplicationUser;
import ru.andreycherenkov.taskmasterserver.db.repository.UserRepository;
import ru.andreycherenkov.taskmasterserver.impl.component.JwtUtil;
import ru.andreycherenkov.taskmasterserver.impl.exception.NotFoundException;
import ru.andreycherenkov.taskmasterserver.impl.exception.PasswordConfirmationException;
import ru.andreycherenkov.taskmasterserver.impl.mapper.UserMapper;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private AuthenticationProvider authenticationProvider;
    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;

    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<UserDtoResponse> createUser(UserCreateDto userCreateDto) {
        validatePassword(userCreateDto);

        userCreateDto.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        ApplicationUser user = userRepository.save(userMapper.toUser(userCreateDto));
        UserDtoResponse userDtoResponse = userMapper.applicationUserToUserDtoResponse(user);
        userDtoResponse.setUserId(user.getId());

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
    public ResponseEntity<AuthResponse> login(AuthRequest authRequest) {
        authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())); //todo бросить HttpStatus.UNAUTHORIZED
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        String jwt = jwtUtil.generateToken(Token.builder()
                .userId(userRepository.findUserByUsername(authRequest.getUsername()).get().getId()) //todo отрефакторить
                .username(userDetails.getUsername())
                .build());
        AuthResponse authResponse = new AuthResponse(jwt);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(authResponse);

    }

    @Override
    public ResponseEntity<UserDtoResponse> getUser(String userId) {
        UUID userUUID = UUID.fromString(userId);
        ApplicationUser user = userRepository.findById(userUUID)
                .orElseThrow(() -> new NotFoundException("User not found"));
        UserDtoResponse dtoResponse = userMapper.applicationUserToUserDtoResponse(user);
        dtoResponse.setUserId(user.getId());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dtoResponse);
    }
}
