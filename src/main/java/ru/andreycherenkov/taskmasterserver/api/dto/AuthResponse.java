package ru.andreycherenkov.taskmasterserver.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.andreycherenkov.taskmasterserver.api.util.OpenApiConstants;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class AuthResponse {

    @Schema(description = "UUID пользователя",
            example = OpenApiConstants.USER_UUID,
            requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("user_id")
    private UUID userId;

    @Schema(description = "jwt-токен",
            example = OpenApiConstants.USER_UUID, //todo аписать пример токена для swagger
            requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("jwt_token")
    private String jwtToken;

}
