package ru.andreycherenkov.taskmasterserver.db.repository;

import org.springframework.data.repository.CrudRepository;
import ru.andreycherenkov.taskmasterserver.db.entity.Task;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends CrudRepository<Task, UUID> {

    List<Task> findAllTasksByUserId(UUID userId);

    Task deleteTaskById(UUID taskId);

}
