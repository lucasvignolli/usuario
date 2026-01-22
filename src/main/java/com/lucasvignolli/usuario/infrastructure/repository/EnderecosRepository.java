package com.lucasvignolli.usuario.infrastructure.repository;

import com.lucasvignolli.usuario.infrastructure.entity.Enderecos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecosRepository extends JpaRepository<Enderecos, Long> {
}
