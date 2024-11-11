package ru.andreycherenkov.taskmasterserver.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.andreycherenkov.taskmasterserver.api.util.OpenApiConstants;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    @Schema(description = "Логин пользователя.",
            example = OpenApiConstants.USERNAME,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "email пользователя.",
            example = OpenApiConstants.EMAIL,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String email; //todo add validation

    @Schema(description = "Пароль пользователя",
            example = OpenApiConstants.PASSWORD,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(description = "Пароль пользователя (подтверждение)",
            example = OpenApiConstants.CONFIRMED_PASSWORD,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String confirmedPassword;

}
