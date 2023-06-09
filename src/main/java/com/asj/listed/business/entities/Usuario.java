package com.asj.listed.business.entities;

import com.asj.listed.business.enums.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
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
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cuenta> cuentas = new ArrayList<>();
}