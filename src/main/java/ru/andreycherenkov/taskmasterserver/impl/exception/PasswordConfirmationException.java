package ru.andreycherenkov.taskmasterserver.impl.exception;

public class PasswordConfirmationException extends RuntimeException {
    public PasswordConfirmationException(String message) {
        super(message);
    }
}
