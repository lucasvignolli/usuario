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
                .id(enderecoDTO.getId())
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .build();
    }

    public List<Telefones> paraListaTelefones(List<TelefonesDTO> telefoneDTOS) {
        return telefoneDTOS.stream().map(this::paraTelefoneEntity).toList();
    }

    public Telefones paraTelefoneEntity(TelefonesDTO telefoneDTO){
        return Telefones.builder()
                .id(telefoneDTO.getId())
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
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .build();
    }

    public List<TelefonesDTO> paraListaTelefonesDTO(List<Telefones> telefoneDTOS) {
        return telefoneDTOS.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefonesDTO paraTelefoneDTO(Telefones telefoneDTOS){
        return TelefonesDTO.builder()
                .id(telefoneDTOS.getId())
                .ddd(telefoneDTOS.getDdd())
                .numero(telefoneDTOS.getNumero())
                .build();
    }
    //_______________________________________________________________
    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario usuarioEntity){
        return Usuario.builder()
                .id(usuarioEntity.getId())
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : usuarioEntity.getNome())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : usuarioEntity.getEmail())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() :  usuarioEntity.getSenha())
                .endereços(usuarioEntity.getEndereços())
                .telefones(usuarioEntity.getTelefones())
                .build();
    }

    public Enderecos updateEndereço(EnderecosDTO enderecosDTO, Enderecos enderecosEntity){
        return Enderecos.builder()
                .id(enderecosEntity.getId())
                .rua(enderecosDTO.getRua() != null ? enderecosDTO.getRua() : enderecosEntity.getRua())
                .numero(enderecosDTO.getNumero() != null ? enderecosDTO.getNumero() : enderecosEntity.getNumero())
                .complemento(enderecosDTO.getComplemento() != null ? enderecosDTO.getComplemento() : enderecosEntity.getComplemento())
                .cep(enderecosDTO.getCep() != null ? enderecosDTO.getCep() : enderecosEntity.getCep())
                .cidade(enderecosDTO.getCidade() != null ? enderecosDTO.getCidade() : enderecosEntity.getCidade())
                .estado(enderecosDTO.getEstado() != null ? enderecosDTO.getEstado() : enderecosEntity.getEstado())
                .build();
    }

    public Telefones updateTelefone(TelefonesDTO telefonesDTO, Telefones telefonsEntity){
        return Telefones.builder()
                .id(telefonsEntity.getId())
                .ddd(telefonesDTO.getDdd() != null ? telefonesDTO.getDdd() : telefonsEntity.getDdd())
                .numero(telefonesDTO.getNumero() != null ? telefonesDTO.getNumero() : telefonsEntity.getNumero())
                .build();
    }
}
