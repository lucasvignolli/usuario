package com.lucasvignolli.usuario.business;

import com.lucasvignolli.usuario.business.converter.UsuarioConverter;
import com.lucasvignolli.usuario.business.dto.EnderecosDTO;
import com.lucasvignolli.usuario.business.dto.TelefonesDTO;
import com.lucasvignolli.usuario.business.dto.UsuarioDTO;
import com.lucasvignolli.usuario.infrastructure.entity.Enderecos;
import com.lucasvignolli.usuario.infrastructure.entity.Telefones;
import com.lucasvignolli.usuario.infrastructure.entity.Usuario;
import com.lucasvignolli.usuario.infrastructure.exceptions.ConflictExceptions;
import com.lucasvignolli.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.lucasvignolli.usuario.infrastructure.exceptions.UnauthorizedExceptions;
import com.lucasvignolli.usuario.infrastructure.repository.EnderecosRepository;
import com.lucasvignolli.usuario.infrastructure.repository.TelefonesRepository;
import com.lucasvignolli.usuario.infrastructure.repository.UsuarioRepository;
import com.lucasvignolli.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder PasswordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecosRepository enderecosRepository;
    private final TelefonesRepository telefonesRepository;
    private final AuthenticationManager authenticationManager;


    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        try {
            if (verificaEmailExistente(usuarioDTO.getEmail())) {
                throw new ConflictExceptions("Email já cadastrado " + usuarioDTO.getEmail());
            }
            usuarioDTO.setSenha(PasswordEncoder.encode(usuarioDTO.getSenha()));
            Usuario usuario = usuarioConverter.paraUsuarioEntity(usuarioDTO);
            usuario = usuarioRepository.save(usuario);
            return usuarioConverter.paraUsuarioDTO(usuario);
        } catch (ConflictExceptions e) {
            throw new ConflictExceptions("Email já cadastrado " + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        try {
            return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não encontrado " + email)));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado " + e.getCause());
        }
    }

    public void deletaUsuario(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {
        //Busca email do usuario atraves do token e retira 7 caracteres (tira a obrigatoriedade de passar o email)
        String email = jwtUtil.extractUsername(token.substring(7));
        //Criptografia de senha
        dto.setSenha(dto.getSenha() != null ? PasswordEncoder.encode(dto.getSenha()) : null);
        //Busca os dados do usuarios no banco de dados
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não localizado "));
        //Mescla os dados que recebemos na requisicao DTO com os dados do banco de dados
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);
        //Cria criptografia na senha novamente
        usuario.setSenha(PasswordEncoder.encode(usuario.getPassword()));
        //Salva os dados do usuario convertido e depois pega o retorno e converte para DTO novamente.
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public EnderecosDTO atualizaEndereco(Long idEndereco, EnderecosDTO enderecosDTO) {
        Enderecos entity = enderecosRepository.findById(idEndereco).orElseThrow(() -> new ResourceNotFoundException("Id não encontrado " + idEndereco));
        Enderecos enderecos = usuarioConverter.updateEndereço(enderecosDTO, entity);
        return usuarioConverter.paraEnderecosDTO(enderecosRepository.save(enderecos));
    }

    public TelefonesDTO atualizaTelefone(Long idTelefone, TelefonesDTO telefonesDTO) {
        Telefones entity = telefonesRepository.findById(idTelefone).orElseThrow(() -> new ResourceNotFoundException("Id não encontrado " + idTelefone));
        Telefones telefones = usuarioConverter.updateTelefone(telefonesDTO, entity);
        return usuarioConverter.paraTelefoneDTO(telefonesRepository.save(telefones));
    }

    public EnderecosDTO cadastraEndereco(EnderecosDTO enderecosDto, String token) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não localizado"));
        Enderecos usuarioEntity = usuarioConverter.paraEndereçoEntityId(enderecosDto, usuario.getId());
        return usuarioConverter.paraEnderecosDTO(enderecosRepository.save(usuarioEntity));
    }

    public TelefonesDTO cadastaTelefone(TelefonesDTO telefoneDto, String token) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não localizado"));
        Telefones telefoneEntity = usuarioConverter.paraTelefoneEntityId(telefoneDto, usuario.getId());
        return usuarioConverter.paraTelefoneDTO(telefonesRepository.save(telefoneEntity));
    }

    public String autenticarUsuario(UsuarioDTO usuarioDTO){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usuarioDTO.getEmail(),
                            usuarioDTO.getSenha()
                    )
            );

            return "Bearer " + jwtUtil.generateToken(authentication.getName());

        } catch (BadCredentialsException | UsernameNotFoundException | AuthorizationDeniedException e) {
            throw new UnauthorizedExceptions("Usuário ou senha invalido: ", e.getCause());

        }
    }
}


