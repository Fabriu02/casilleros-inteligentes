package com.casilleros_inteligentes.repositorio;

import com.casilleros_inteligentes.modelos.Casillero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface repocasillero extends JpaRepository<Casillero, Long> {

}