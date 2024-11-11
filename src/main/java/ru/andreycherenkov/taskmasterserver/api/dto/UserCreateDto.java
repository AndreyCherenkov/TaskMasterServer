package ru.andreycherenkov.taskmasterserver.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("username")
    private String username;

    @Schema(description = "email пользователя.",
            example = OpenApiConstants.EMAIL,
            requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("email")
    private String email; //todo add validation

    @Schema(description = "Пароль пользователя",
            example = OpenApiConstants.PASSWORD,
            requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("password")
    private String password;

    @Schema(description = "Пароль пользователя (подтверждение)",
            example = OpenApiConstants.CONFIRMED_PASSWORD,
            requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("confirmed_password")
    private String confirmedPassword;

}
