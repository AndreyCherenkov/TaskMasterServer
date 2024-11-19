package ru.andreycherenkov.taskmasterserver.impl.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class); //todo @Sl4j

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

    private String generateResponse(Exception ex, HttpStatus httpStatus) {

        ObjectMapper mapper = new ObjectMapper();
        ExceptionDtoResponse exceptionDtoResponse = ExceptionDtoResponse.builder()
                .message(ex.getMessage())
                .httpStatus(String.valueOf(httpStatus))
                .time(LocalDateTime.now().toString())
                .build();

        String response = null;
        try {
            response = mapper.writeValueAsString(exceptionDtoResponse);
        } catch (JsonProcessingException e) {
            logger.error(ex.getLocalizedMessage(), e);
        }

        return response;
    }
}
