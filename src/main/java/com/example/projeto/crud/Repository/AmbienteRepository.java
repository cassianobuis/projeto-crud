package com.example.projeto.crud.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.projeto.crud.Entity.Ambiente;

@Repository
public interface AmbienteRepository extends BaseRepository<Ambiente, Long> {

    @Query("""
            SELECT COUNT(r) > 0 
            FROM Reserva r
            WHERE r.ambiente.id = :id 
            AND (r.dataInicio > CURRENT_TIMESTAMP OR r.dataFim > CURRENT_TIMESTAMP)
            AND r.ativo = true 
            """)
    boolean temReserva(Long id);

}
