package com.asj.listed.business.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "titulares")
public class Titular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(name = "cuit")
    private String cuit;

    @NotBlank
    @Column(name = "email1")
    private String email1;

    @Column(name = "email2")
    private String email2;

    @NotBlank
    @Column(name = "nombres")
    private String nombres;

    @ManyToOne()
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "titular")
    private List<Cuenta> cuentas;
}
