package com.example.projeto.crud.Entity;

import java.time.LocalDateTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reserva")
@Entity
@EqualsAndHashCode(callSuper = false)
public class Reserva extends BaseEntity {

    
    @Column(nullable = false)
    private String motivo;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDateTime dataInicio;

    @Column(nullable = false)
    private LocalDateTime dataFim;

    @ManyToOne
    private Ambiente ambiente;

    public static LocalDateTime dataInicio() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dataInicio'");
    }

}
