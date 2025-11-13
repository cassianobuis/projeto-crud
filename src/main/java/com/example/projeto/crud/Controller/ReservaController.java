package com.example.projeto.crud.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.projeto.crud.DTO.ReservaDTO;
import com.example.projeto.crud.Entity.Reserva;
import com.example.projeto.crud.Service.ReservaService;

@RestController
@RequestMapping("/reservas")
public class ReservaController extends BaseController<ReservaDTO> {

    private ReservaService service;

    protected ReservaController(ReservaService service) {
        super(service);
        this.service = service;
    }

    @GetMapping("/porData/{dataInicio}/{dataFim}")
    public List<ReservaDTO> getReservasPorData(
            @PathVariable("dataInicio") String dataInicio,
            @PathVariable("dataFim") String dataFim
            ) {
                return service.listaPorData(dataInicio, dataFim);
    }

    @GetMapping("/porAmbiente/{ambienteId}")
    public List<ReservaDTO> getReservasPorAmbiente(@PathVariable("ambienteId") Long ambienteId) {
        return service.listaPorAmbiente(ambienteId);
    }

    @GetMapping("/porUsuario/{nomeUsuario}")
    public List<ReservaDTO> getReservasPorUsuario(@PathVariable String nomeUsuario) {
        List<Reserva> reservas = service.findReservasPorNomeUsuario(nomeUsuario);
        List<ReservaDTO> reservaDTOs = new ArrayList<>();

        for (Reserva reserva : reservas) {
            reservaDTOs.add(service.toDto(reserva));
        }
        return reservaDTOs;
    }

}