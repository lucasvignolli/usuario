package com.lucasvignolli.usuario.infrastructure.repository;

import com.lucasvignolli.usuario.infrastructure.entity.Telefones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefonesRepository extends JpaRepository<Telefones, Long> {
}
