package com.asj.listed.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Table(name = "cuentas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alias")
    private String alias;

    @Column(name = "banco")
    private String banco;

    @Column(name = "cbu", unique = true)
    private String cbu;

    @Column(name = "nro_cuenta")
    private String nroCuenta;

    @Column(name = "sucursal")
    private String sucursal;

    @ManyToOne()
    @JoinColumn(name = "id_titular", nullable = false)
    private Holder titular;

    @ManyToOne()
    @JoinColumn(name = "id_usuario")
    private User usuario;
}