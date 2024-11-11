package ru.andreycherenkov.taskmasterserver.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.andreycherenkov.taskmasterserver.api.util.OpenApiConstants;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoResponse {

    @Schema(description = "UUID пользователя",
            example = OpenApiConstants.USER_UUID,
            requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("user_id")
    private UUID userId;

    @Schema(description = "Логин пользователя",
            example = OpenApiConstants.USERNAME,
            requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("username")
    private String username;

    @Schema(description = "e-mail пользователя",
           example = OpenApiConstants.EMAIL,
           requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("email")
    private String email;
}
