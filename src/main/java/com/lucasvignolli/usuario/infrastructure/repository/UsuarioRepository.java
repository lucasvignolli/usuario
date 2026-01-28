package com.lucasvignolli.usuario.infrastructure.repository;

import com.lucasvignolli.usuario.infrastructure.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //Consulta no banco de dados se o email existe e retorna um verdadeiro ou falso.
    boolean existsByEmail(String email);

    //Consulta se o email existe.
    Optional<Usuario> findByEmail(String email);

    //Deleta usuario por email
    @Transactional
    void deleteByEmail (String email);

    
}
