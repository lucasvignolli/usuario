package com.lucasvignolli.usuario.infrastructure.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "telefones")
@Builder
public class Telefones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ddd", length = 3)
    private String ddd;
    @Column(name = "numero", length = 10)
    private String numero;
    @Column(name = "usuario_id")
    private Long usuario_id;
}
