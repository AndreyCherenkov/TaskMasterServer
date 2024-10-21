package ru.andreycherenkov.taskmasterserver.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.andreycherenkov.taskmasterserver.api.dto.UserCreateDto;
import ru.andreycherenkov.taskmasterserver.api.dto.UserDtoResponse;
import ru.andreycherenkov.taskmasterserver.db.entity.ApplicationUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        imports = {
                LocalDate.class,
                LocalDateTime.class,
                UUID.class
        }
)
public interface UserMapper {

    UserDtoResponse applicationUserToUserDtoResponse(ApplicationUser applicationUser);

    @Mapping(target = "createdAt", expression = "java(LocalDate.now())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    ApplicationUser toUser(UserCreateDto userCreateDto);

}
