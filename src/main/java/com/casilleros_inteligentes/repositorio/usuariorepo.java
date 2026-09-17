package com.casilleros_inteligentes.repositorio;

import com.casilleros_inteligentes.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface usuariorepo extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email); // buscar por mail
}