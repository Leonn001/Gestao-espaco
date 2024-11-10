package com.ucsal.gestao_espaco_fisico.repository;

import com.ucsal.gestao_espaco_fisico.models.entitys.Space;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceRepository extends JpaRepository<Space, Long> {
}