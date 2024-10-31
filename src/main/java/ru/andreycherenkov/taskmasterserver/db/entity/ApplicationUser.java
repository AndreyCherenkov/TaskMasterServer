package ru.andreycherenkov.taskmasterserver.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "application_user")
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "username",
            unique = true,
            nullable = false)
    private String username;

    @Column(name = "email",
            unique = true)
    private String email;

    @Column(name = "password",
            nullable = false)
    private String password;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "userId")
    private List<Task> tasks;
}
