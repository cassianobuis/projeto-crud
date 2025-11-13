package com.example.projeto.crud.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.projeto.crud.DTO.AmbienteDTO;
import com.example.projeto.crud.Entity.Ambiente;
import com.example.projeto.crud.Entity.Reserva;
import com.example.projeto.crud.Repository.AmbienteRepository;
import com.example.projeto.crud.Repository.ReservaRepository;

@Service
public class AmbienteService extends BaseService<Ambiente, AmbienteDTO> {

    private AmbienteRepository repository;

    
    protected AmbienteService(AmbienteRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public void delete(Long id) {
        boolean reservado = repository.temReserva(id);

        if(reservado) {
            throw new IllegalStateException("Ambiente possui reservas futuras e ambiente não pode ser excluído.");
        }
        super.delete(id);
        }


}
