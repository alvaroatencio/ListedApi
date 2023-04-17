package com.asj.listed.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "titulares")
public class Holder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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
    private User user;

    @OneToMany(mappedBy = "titular")
    private List<Account> accounts;
}
