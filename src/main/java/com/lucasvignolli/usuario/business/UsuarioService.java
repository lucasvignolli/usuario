package com.lucasvignolli.usuario.business;

import com.lucasvignolli.usuario.business.converter.UsuarioConverter;
import com.lucasvignolli.usuario.business.dto.UsuarioDTO;
import com.lucasvignolli.usuario.infrastructure.entity.Usuario;
import com.lucasvignolli.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;


    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioConverter.paraUsuarioEntity(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioConverter.paraUsuarioDTO(usuario);
    }
}
