package ru.andreycherenkov.taskmasterserver.db.repository;

import org.springframework.data.repository.CrudRepository;
import ru.andreycherenkov.taskmasterserver.db.entity.ApplicationUser;

import java.util.UUID;

public interface UserRepository extends CrudRepository<ApplicationUser, UUID> {
}
