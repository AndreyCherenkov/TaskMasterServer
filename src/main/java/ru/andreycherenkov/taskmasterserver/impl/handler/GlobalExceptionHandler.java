package ru.andreycherenkov.taskmasterserver.impl.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.andreycherenkov.taskmasterserver.api.dto.ExceptionDtoResponse;
import ru.andreycherenkov.taskmasterserver.impl.exception.NotFoundException;
import ru.andreycherenkov.taskmasterserver.impl.exception.PasswordConfirmationException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(generateResponse(ex, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(generateResponse(ex, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(PasswordConfirmationException.class)
    public ResponseEntity<String> handlePasswordConfirmationException(PasswordConfirmationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(generateResponse(ex, HttpStatus.BAD_REQUEST));
    }

    //todo парсить в формат JSON
    private String generateResponse(Exception ex, HttpStatus httpStatus) {
        return ExceptionDtoResponse.builder()
                .message(ex.getMessage())
                .httpStatus(String.valueOf(httpStatus))
                .time(LocalDateTime.now().toString())
                .build()
                .toString();
    }
}
