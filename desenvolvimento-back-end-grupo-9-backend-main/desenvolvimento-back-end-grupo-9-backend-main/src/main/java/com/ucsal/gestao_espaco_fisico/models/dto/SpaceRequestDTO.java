package com.ucsal.gestao_espaco_fisico.models.dto;

import com.ucsal.gestao_espaco_fisico.models.entitys.Space;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceRequestDTO {
    private Space space; // O espaço que está sendo solicitado
    private LocalDateTime startDate; // Data e hora de início da reserva
    private LocalDateTime endDate; // Data e hora de término da reserva
}