package com.casilleros_inteligentes.repositorio;

import com.casilleros_inteligentes.modelos.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface reporeserva extends JpaRepository<Reserva, Long> {

    // Para la regla 1: Saber si el estudiante YA tiene una reserva activa
    boolean existsByUsuarioIdAndEstado(Long usuarioId, String estado);

    // Para la regla 2: Saber los datos de la reserva de un casillero específico
    Optional<Reserva> findByCasilleroIdAndEstado(Long casilleroId, String estado);

}