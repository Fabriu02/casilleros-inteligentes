package com.casilleros_inteligentes.repositorio;
import com.casilleros_inteligentes.modelos.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface reporeserva extends JpaRepository<Reserva, Long> {
    boolean existsByUsuarioIdAndEstado(Long usuarioId, Integer estado);
    Optional<Reserva> findByCasilleroIdAndEstado(Long casilleroId, Integer estado);
}