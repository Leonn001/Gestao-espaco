package com.ucsal.gestao_espaco_fisico.models.entitys;

import com.ucsal.gestao_espaco_fisico.models.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "approval_flows")
public class ApprovalFlow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private SpaceRequest request;  // Referência à solicitação de espaço

    @ManyToOne
    @JoinColumn(name = "approved_by", nullable = false)
    private User approvedBy;  // O gestor que aprovou ou rejeitou a solicitação

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status decision;

    @Column(nullable = false)
    private LocalDateTime decisionDate;  // Data e hora da decisão
}