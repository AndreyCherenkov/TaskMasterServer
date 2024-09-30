package ru.andreycherenkov.taskmasterserver.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

    @Column(name = "username",
            unique = true,
            nullable = false)
    private String username;

    @Column(name = "password",
            nullable = false)
    private String password;

    @OneToMany
    @JoinTable(
            name = "", //todo прописать соединение юзеров с тасками
            joinColumns = @JoinColumn(name = "")
    )
    private List<Task> tasks;
}
