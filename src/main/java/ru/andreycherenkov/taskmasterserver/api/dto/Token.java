package ru.andreycherenkov.taskmasterserver.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Token {

    private UUID userId;
    private String username;
//    private List<String> authorities;
//    private Instant createdAt;
//    private Instant expiresAt;

}
