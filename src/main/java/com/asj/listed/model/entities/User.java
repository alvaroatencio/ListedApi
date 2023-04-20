package com.asj.listed.model.entities;

import com.asj.listed.model.enums.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;


@Entity
@Builder
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 5, message = "El usuario debe tener al menos 5 caracteres")
    @Column(name = "usuario", nullable = false, unique = true)
    private String usuario;
    @NotBlank
    @Column(name = "mail", nullable = false, unique = true)
    private String mail;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "token")
    private Integer token;
    @Column(name = "rol", nullable = false)
    @Enumerated(EnumType.STRING)
    private Rol rol;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Account> accounts = new ArrayList<>();
}