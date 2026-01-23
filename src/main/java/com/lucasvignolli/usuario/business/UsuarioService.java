package com.lucasvignolli.usuario.business;

import com.lucasvignolli.usuario.business.converter.UsuarioConverter;
import com.lucasvignolli.usuario.business.dto.UsuarioDTO;
import com.lucasvignolli.usuario.infrastructure.entity.Usuario;
import com.lucasvignolli.usuario.infrastructure.exceptions.ConflictExceptions;
import com.lucasvignolli.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
@RequiredArgsConstructor

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder bCryptPasswordEncoder;


    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        try {
            if (verificaEmailExistente(usuarioDTO.getEmail())) {
                throw new ConflictExceptions("Email já cadastrado" + usuarioDTO.getEmail());
            }
            usuarioDTO.setSenha(bCryptPasswordEncoder.encode(usuarioDTO.getSenha()));
            Usuario usuario = usuarioConverter.paraUsuarioEntity(usuarioDTO);
            usuario = usuarioRepository.save(usuario);
            return usuarioConverter.paraUsuarioDTO(usuario);
        } catch (ConflictExceptions e) {
            throw new ConflictExceptions("Email já cadastrado" + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

//    public Usuario buscarUsuarioPorEmail(String email){
//        return usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceAccessException("Email não encontrado " + email));
//    }

    public UsuarioDTO buscarUsuarioPorEmail(String email){
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceAccessException("Email não encontrado " + email));
        return usuarioConverter.paraUsuarioDTO(usuario);
    }

    public void deletaUsuario(String email){
        usuarioRepository.deleteByEmail(email);
    }
}

