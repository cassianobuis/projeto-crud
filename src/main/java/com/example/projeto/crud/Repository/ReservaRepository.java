package com.example.projeto.crud.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.projeto.crud.Entity.Reserva;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends BaseRepository<Reserva, Long> {

        @Query("SELECT CASE WHEN COUNT(r) = 0 THEN true ELSE false END " +
                        "FROM Reserva r " +
                        "WHERE r.ambiente.id = :id " +
                        "AND (r.dataInicio < :dataFim AND r.dataFim > :dataInicio)")
        boolean ambienteDisponivel(Long id, LocalDateTime dataInicio, LocalDateTime dataFim);

        @Query("""
                        SELECT r
                        From Reserva r
                        WHERE r.ativo = true
                        AND (r.dataInicio BETWEEN :dataInicio AND :dataFim
                        OR r.dataFim BETWEEN :dataInicio AND :dataFim)
                        """)
        List<Reserva> findByDatas(LocalDateTime dataInicio, LocalDateTime dataFLocalDateTime);

        @Query("""
                SELECT r
                FROM Reserva r
                WHERE r.ativo = true
                AND r.ambiente.id = :ambienteId
                """)
        List<Reserva> findByAmbiente(Long ambienteId);

        @Query("SELECT r FROM Reserva r WHERE r.nome = :nomeUsuario")
        List<Reserva> findReservasPorNomeUsuario(@Param("nomeUsuario") String nomeUsuario);

}
