package com.lucasvignolli.usuario.business.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TelefonesDTO {

    private Long id;
    private String ddd;
    private String numero;

}
