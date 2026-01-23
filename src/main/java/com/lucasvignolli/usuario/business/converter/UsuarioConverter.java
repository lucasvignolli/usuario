package com.lucasvignolli.usuario.business.converter;


import com.lucasvignolli.usuario.business.dto.EnderecosDTO;
import com.lucasvignolli.usuario.business.dto.TelefonesDTO;
import com.lucasvignolli.usuario.business.dto.UsuarioDTO;
import com.lucasvignolli.usuario.infrastructure.entity.Enderecos;
import com.lucasvignolli.usuario.infrastructure.entity.Telefones;
import com.lucasvignolli.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class UsuarioConverter {

    public Usuario paraUsuarioEntity(UsuarioDTO usuarioDT0) {
        return Usuario.builder()
                .nome(usuarioDT0.getNome())
                .email(usuarioDT0.getEmail())
                .senha(usuarioDT0.getSenha())
                .endereços(paraListaEnderecoEntity(usuarioDT0.getEnderecos()))
                .telefones(paraListaTelefones(usuarioDT0.getTelefones()))
                .build();
    }

    public List<Enderecos> paraListaEnderecoEntity(List<EnderecosDTO> enderecoDTOs){
        return enderecoDTOs.stream().map(this::paraEnderecosEntity).toList();
    }

    public Enderecos paraEnderecosEntity(EnderecosDTO enderecoDTO) {
        return Enderecos.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .build();
    }


    public List<Telefones> paraListaTelefones(List<TelefonesDTO> telefoneDTOS) {
        return telefoneDTOS.stream().map(this::paraTelefone).toList();
    }

    public Telefones paraTelefone(TelefonesDTO telefoneDTO){
        return Telefones.builder()
                .ddd(telefoneDTO.getDdd())
                .numero(telefoneDTO.getNumero())
                .build();
    }

    //_______________________________________________________________


    public UsuarioDTO paraUsuarioDTO(Usuario usuarioDT0) {
        return UsuarioDTO.builder()
                .nome(usuarioDT0.getNome())
                .email(usuarioDT0.getEmail())
                .senha(usuarioDT0.getSenha())
                .enderecos(paraListaEnderecoDTO(usuarioDT0.getEndereços()))
                .telefones(paraListaTelefonesDTO(usuarioDT0.getTelefones()))
                .build();
    }

    public List<EnderecosDTO> paraListaEnderecoDTO(List<Enderecos> endereco){
        return endereco.stream().map(this::paraEnderecosDTO).toList();
    }

    public EnderecosDTO paraEnderecosDTO(Enderecos endereco) {
        return EnderecosDTO.builder()
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .build();
    }


    public List<TelefonesDTO> paraListaTelefonesDTO(List<Telefones> telefoneDTOS) {
        return telefoneDTOS.stream().map(this::paraTelefone).toList();
    }

    public TelefonesDTO paraTelefone(Telefones telefoneDTOS){
        return TelefonesDTO.builder()
                .ddd(telefoneDTOS.getDdd())
                .numero(telefoneDTOS.getNumero())
                .build();
    }



}
