package ru.andreycherenkov.taskmasterserver.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.andreycherenkov.taskmasterserver.api.util.OpenApiConstants;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {

    @Schema(description = "Имя пользователя.",
            example = OpenApiConstants.FIRSTNAME,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;

    @Schema(description = "Фамилия пользователя.",
            example = OpenApiConstants.LASTNAME,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String lastName;

    @Schema(description = "email пользователя.",
            example = OpenApiConstants.EMAIL,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String email; //todo add validation

    @Schema(description = "Логин пользователя.",
            example = OpenApiConstants.USERNAME,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "Пароль пользователя",
            example = OpenApiConstants.PASSWORD,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String password; //todo передаём пароль напрямую?

    @Schema(description = "Пароль пользователя (подтверждение)",
            example = OpenApiConstants.CONFIRMED_PASSWORD,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String confirmedPassword;

    @Schema(description = "Возраст пользователя.",
            example = OpenApiConstants.AGE,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer age;

}
