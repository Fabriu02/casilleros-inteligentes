package com.casilleros_inteligentes.repositorio;
import com.casilleros_inteligentes.modelos.TarjetaRFID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RepoTarjetaRFID extends JpaRepository<TarjetaRFID, Long> {
    Optional<TarjetaRFID> findByUidAndEstado(String uid, Integer estado);
}