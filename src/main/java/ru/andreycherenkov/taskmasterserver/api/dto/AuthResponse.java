package ru.andreycherenkov.taskmasterserver.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.andreycherenkov.taskmasterserver.api.util.OpenApiConstants;

@Setter
@Getter
@AllArgsConstructor
public class AuthResponse {

    @Schema(description = "jwt-токен",
            example = OpenApiConstants.USER_UUID, //todo аписать пример токена для swagger
            requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("jwt_token")
    private String jwtToken;

}
