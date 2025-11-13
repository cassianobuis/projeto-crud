package com.example.projeto.crud.DTO;

import java.time.LocalDateTime;

import com.example.projeto.crud.Entity.Ambiente;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "O motivo não pode ser vazio")
    private String motivo;

    @NotBlank(message = "O nome não pode ser vazio")
    private String nome;

    @NotNull(message = "A data de início não pode ser nula")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataInicio;

    @NotNull(message = "A data de fim não pode ser nula")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataFim;

    @NotNull(message = "O ambiente não pode ser nulo")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Ambiente ambiente;

    @AssertTrue(message = "A data de início deve ser anterior à data de fim")
    public boolean periodoValido() {
        return dataInicio.isBefore(dataFim);
    }

}
