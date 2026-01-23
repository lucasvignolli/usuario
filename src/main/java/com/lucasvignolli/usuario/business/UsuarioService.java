package com.lucasvignolli.usuario.business;

import com.lucasvignolli.usuario.business.converter.UsuarioConverter;
import com.lucasvignolli.usuario.business.dto.UsuarioDTO;
import com.lucasvignolli.usuario.infrastructure.entity.Usuario;
import com.lucasvignolli.usuario.infrastructure.exceptions.ConflictExceptions;
import com.lucasvignolli.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.lucasvignolli.usuario.infrastructure.repository.UsuarioRepository;
import com.lucasvignolli.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
@RequiredArgsConstructor

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder PasswordEncoder;
    private final JwtUtil jwtUtil;


    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        try {
            if (verificaEmailExistente(usuarioDTO.getEmail())) {
                throw new ConflictExceptions("Email já cadastrado" + usuarioDTO.getEmail());
            }
            usuarioDTO.setSenha(PasswordEncoder.encode(usuarioDTO.getSenha()));
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

    public UsuarioDTO buscarUsuarioPorEmail(String email){
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceAccessException("Email não encontrado " + email));
        return usuarioConverter.paraUsuarioDTO(usuario);
    }

    public void deletaUsuario(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario (String token, UsuarioDTO dto){
        //Busca email do usuario atraves do token e retira 7 caracteres (tira a obrigatoriedade de passar o email)
        String email = jwtUtil.extractUsername(token.substring(7));

        //Criptografia de senha
        dto.setSenha(dto.getSenha() != null ? PasswordEncoder.encode(dto.getSenha()) : null);

        //Busca os dados do usuarios no banco de dados
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não localizado"));

        //Mescla os dados que recebemos na requisicao DTO com os dados do banco de dados
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

        //Cria criptografia na senha novamente
        usuario.setSenha(PasswordEncoder.encode(usuario.getPassword()));

        //Salva os dados do usuario convertido e depois pega o retorno e converte para DTO novamente.
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
}

