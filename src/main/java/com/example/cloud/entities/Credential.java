package com.example.cloud.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "credential")
public class Credential implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @NotNull
    private Long id;
    @NonNull
    @Id
    @Column(name = "login")
    private String login;
    @NotNull
    @OneToOne
    @JoinColumn(name = "login")
    private User user;
    private char @NonNull [] password;
}