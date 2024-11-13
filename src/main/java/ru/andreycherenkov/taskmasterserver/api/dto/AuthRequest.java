package ru.andreycherenkov.taskmasterserver.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.andreycherenkov.taskmasterserver.api.util.OpenApiConstants;

@Getter
@Setter
@AllArgsConstructor
public class AuthRequest {

    @Schema(description = "Логин пользователя.",
            example = OpenApiConstants.USERNAME,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    @Schema(description = "Пароль пользователя.",
            example = OpenApiConstants.PASSWORD,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

}
