package com.ucsal.gestao_espaco_fisico.models.dto;

import com.ucsal.gestao_espaco_fisico.models.enums.SpaceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpaceDTO {
    private String location;
    private String name;
    private Integer capacity;
    private SpaceType type;
    private List<String> resources;
}
