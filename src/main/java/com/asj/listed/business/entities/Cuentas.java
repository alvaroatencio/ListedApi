package com.asj.listed.business.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cuentas")
public class Cuentas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "alias")
    private String alias;

    @Column(name = "banco")
    private String banco;

    @Column(name = "cbu")
    private String cbu;

    @Column(name = "nro_cuenta")
    private String nroCuenta;

    @Column(name = "sucursal")
    private String sucursal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_titular", nullable = false)
    private Titulares titular;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuarios usuario;
}