package com.lucasvignolli.usuario.controller;


import com.lucasvignolli.usuario.business.UsuarioService;
import com.lucasvignolli.usuario.business.dto.EnderecosDTO;
import com.lucasvignolli.usuario.business.dto.TelefonesDTO;
import com.lucasvignolli.usuario.business.dto.UsuarioDTO;
import com.lucasvignolli.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.osgi.annotation.bundle.Header;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuario")

public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuarioDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<UsuarioDTO> buscaUsuario(@RequestParam("email") String email){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email){
        usuarioService.deletaUsuario(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizaDadosDeUsuario(@RequestBody UsuarioDTO dto,
                                                             @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, dto));
    }

    @PutMapping("/enderecos")
    public ResponseEntity<EnderecosDTO> atualizaDadosDeEndereco(@RequestBody EnderecosDTO dto,
                                                               @RequestParam("id") Long id) {
        return ResponseEntity.ok(usuarioService.atualizaEndereco(id, dto));
    }

    @PutMapping("/telefones")
    public ResponseEntity<TelefonesDTO> atualizaDadosDeTelefone(@RequestBody TelefonesDTO dto,
                                                                @RequestParam("id") Long id){
    return ResponseEntity.ok(usuarioService.atualizaTelefone(id, dto));
    }

    @PostMapping("/enderecos")
    public ResponseEntity<EnderecosDTO> cadastraNovoEndereço(@RequestBody EnderecosDTO dto,
                                                             @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastraEndereco(dto, token));
    }

    @PostMapping("/telefones")
    public ResponseEntity<TelefonesDTO> cadastraNovoTelefone(@RequestBody TelefonesDTO dto,
                                                          @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastaTelefone(dto, token));
    }
}
