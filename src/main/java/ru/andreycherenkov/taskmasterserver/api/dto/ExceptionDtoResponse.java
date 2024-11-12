package ru.andreycherenkov.taskmasterserver.api.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDtoResponse {
    private String message;
    private String httpStatus;
    private String time;
}
