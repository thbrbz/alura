package com.thbrbz.checkpointNivel1.repositories;

import com.thbrbz.checkpointNivel1.entities.Reserva;
import com.thbrbz.checkpointNivel1.enums.ReservaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT COUNT(r) > 0 FROM Reserva r " +
            "WHERE r.sala.id = :salaId " +
            "AND r.status = :status " +
            "AND r.dataInicio < :fim " +
            "AND r.dataFim > :inicio")
    boolean existeConflitoDeHorario(
            @Param("salaId") Long salaId,
            @Param("status") ReservaStatus status,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );

    List<Reserva> findByUsuariosId(Long id);

    List<Reserva> findBySalaId(Long id);
}
