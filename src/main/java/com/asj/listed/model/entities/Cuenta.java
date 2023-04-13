package com.asj.listed.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Table(name = "cuentas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @ManyToOne()
    @JoinColumn(name = "id_titular", nullable = false)
    private Titular titular;

    @ManyToOne()
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}