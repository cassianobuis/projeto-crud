package com.example.projeto.crud.Service;

import org.springframework.beans.factory.annotation.Autowired;x 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.projeto.crud.DTO.ReservaDTO;
import com.example.projeto.crud.Entity.Ambiente;
import com.example.projeto.crud.Entity.Reserva;
import com.example.projeto.crud.Repository.AmbienteRepository;
import com.example.projeto.crud.Repository.ReservaRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService extends BaseService<Reserva, ReservaDTO> {

    public ReservaRepository reservaRepository;

    @Autowired
    AmbienteRepository ambienteRepository;

    protected ReservaService(ReservaRepository repository) {
        super(repository);
        this.reservaRepository = repository;
    }

    @Transactional
    @Override
    public ReservaDTO create(ReservaDTO dto) {
        Ambiente ambiente = ambienteRepository.findById(dto.getAmbiente().getId())
                .orElseThrow(() -> new IllegalStateException("Ambiente não encontrado."));
        if (!ambiente.isAtivo()) {
            throw new IllegalStateException("O ambiente não está ativo.");
        }

        Reserva reserva = toEntity(dto);

        boolean isDisponivel = reservaRepository.ambienteDisponivel(
                reserva.getAmbiente().getId(),
                reserva.getDataInicio(),
                reserva.getDataFim());

        if (!isDisponivel) {
            throw new IllegalStateException("O ambiente não está disponível nesse intervalo de tempo.");
        }

        reserva = reservaRepository.save(reserva);
        return super.create(dto);
    }



    @Transactional
    public ReservaDTO update(Long id, ReservaDTO dto) {
        Reserva reservaExistente = reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Reserva não encontrada."));

        if (reservaExistente.getDataInicio().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Não é possível editar uma reserva que já iniciou.");
        }

        return super.update(id, dto);
    }





    public List<ReservaDTO> listaPorData(String dataInicio, String dataFim) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime inicio = LocalDate.parse(dataInicio, formatter).atStartOfDay();
        LocalDateTime fim = LocalDate.parse(dataFim, formatter).atTime(23, 59, 59);

        List<Reserva> reservas = reservaRepository.findByDatas(inicio, fim);
        List<ReservaDTO> dtos = new ArrayList<>();

        for (Reserva reserva : reservas) {
            dtos.add(super.toDto(reserva));
        }
        return dtos;
    }





    public List<ReservaDTO> listaPorAmbiente(Long ambienteId) {
        List<Reserva> reservas = reservaRepository.findByAmbiente(ambienteId);

        List<ReservaDTO> dtos = new ArrayList<>();

        for (Reserva reserva : reservas) {
            dtos.add(super.toDto(reserva));
        }
        return dtos;
    }



    public List<ReservaDTO> listarPorUsuario(String nome) {
        List<Reserva> reservas = reservaRepository.findByUsuario(nome);

        List<ReservaDTO> dtos = new ArrayList<>();

        for (Reserva reserva : reservas){
            dtos.add(super.toDto(reserva));
        }
        return dtos;
    }

}
