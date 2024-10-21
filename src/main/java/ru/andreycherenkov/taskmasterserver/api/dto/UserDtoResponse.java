package ru.andreycherenkov.taskmasterserver.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoResponse {
    //todo add @Schema
    private UUID id;
    private String username;
    private String email;
}
