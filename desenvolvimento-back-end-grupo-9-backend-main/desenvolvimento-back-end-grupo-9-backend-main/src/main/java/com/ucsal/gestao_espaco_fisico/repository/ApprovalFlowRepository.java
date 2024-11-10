package com.ucsal.gestao_espaco_fisico.repository;

import com.ucsal.gestao_espaco_fisico.models.entitys.ApprovalFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalFlowRepository extends JpaRepository<ApprovalFlow, Long> {
}
