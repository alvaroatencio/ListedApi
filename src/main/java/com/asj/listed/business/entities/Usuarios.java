package com.asj.listed.business.entities;

import com.asj.listed.business.models.Rol;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NotBlank
    @Column(name = "usuario", nullable = false, unique = true)
    private String usuario;
    @NotBlank
    @Column(name = "mail", nullable = false, unique = true)
    private String mail;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "token")
    Integer token;
    @Column(name = "rol", nullable = false)
    @Enumerated(EnumType.STRING)
    private Rol rol;
    @OneToMany(mappedBy = "usuario")
    private List<Cuentas> cuentas;
}
