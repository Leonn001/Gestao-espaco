package com.ucsal.gestao_espaco_fisico.service;

import com.ucsal.gestao_espaco_fisico.models.dto.SpaceRequestDTO;
import com.ucsal.gestao_espaco_fisico.models.entitys.ApprovalFlow;
import com.ucsal.gestao_espaco_fisico.models.entitys.SpaceRequest;
import com.ucsal.gestao_espaco_fisico.models.entitys.User;
import com.ucsal.gestao_espaco_fisico.models.enums.Status;
import com.ucsal.gestao_espaco_fisico.models.enums.Role;
import com.ucsal.gestao_espaco_fisico.repository.ApprovalFlowRepository;
import com.ucsal.gestao_espaco_fisico.repository.SpaceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SpaceRequestService {

    private final SpaceRequestRepository spaceRequestRepository;
    private final ApprovalFlowRepository approvalFlowRepository;

    @Autowired
    public SpaceRequestService(SpaceRequestRepository spaceRequestRepository, ApprovalFlowRepository approvalFlowRepository) {
        this.spaceRequestRepository = spaceRequestRepository;
        this.approvalFlowRepository = approvalFlowRepository;
    }

    public List<SpaceRequest> getAllSpaceRequests() {
        return spaceRequestRepository.findAll();
    }

    public List<ApprovalFlow> getAllAprovaFlows() {
        return approvalFlowRepository.findAll();
    }

    @Transactional
    public SpaceRequest createSpaceRequest(SpaceRequestDTO spaceRequestDTO, User user) {
        if (!user.getRole().equals(Role.TEACHER)) {
            throw new RuntimeException("Only teachers can create space requests");
        }

        // Valida as datas da solicitação
        if (spaceRequestDTO.getStartDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Start date cannot be in the past");
        }
        if (spaceRequestDTO.getEndDate().isBefore(spaceRequestDTO.getStartDate())) {
            throw new RuntimeException("End date must be after start date");
        }


       SpaceRequest spaceRequest = SpaceRequest.builder()
                .requestedBy(user)
                .space(spaceRequestDTO.getSpace())
                .requestDate(LocalDateTime.now())
                .startDate(spaceRequestDTO.getStartDate())
                .endDate(spaceRequestDTO.getEndDate())
                .status(Status.PENDENTE)
                .build();

        // Salva a solicitação no banco de dados
        return spaceRequestRepository.save(spaceRequest);
    }


    @Transactional
    public ApprovalFlow approveRequest(Long requestId, User approver) {
        // Verifica se o usuário é um gestor
        if (!approver.getRole().equals(Role.MANAGER)) {
            throw new RuntimeException("Only managers can approve space requests");
        }

        // Busca a solicitação de espaço pelo ID
        SpaceRequest spaceRequest = spaceRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Space request not found"));

        if (!spaceRequest.getSpace().getIsAvailable()){
            throw new RuntimeException("This space is not available");
        }
        spaceRequest.getSpace().setIsAvailable(false);
        spaceRequest.setStatus(Status.APROVADO);

        // Salva a solicitação atualizada
        spaceRequestRepository.save(spaceRequest);

        // Cria um registro de aprovação
        ApprovalFlow approvalFlow = ApprovalFlow.builder()
                .request(spaceRequest)
                .approvedBy(approver)
                .decision(Status.APROVADO)
                .decisionDate(LocalDateTime.now())
                .build();

        // Salva o registro de aprovação
        return approvalFlowRepository.save(approvalFlow);
    }

    @Transactional
    public ApprovalFlow rejectRequest(Long requestId, User approver) {
        // Verifica se o usuário é um gestor
        if (!approver.getRole().equals(Role.MANAGER)) {
            throw new RuntimeException("Only managers can reject space requests");
        }

        // Busca a solicitação de espaço pelo ID
        SpaceRequest spaceRequest = spaceRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Space request not found"));

        if (!spaceRequest.getSpace().getIsAvailable()){
            throw new RuntimeException("This space is not available");
        }

        spaceRequest.getSpace().setIsAvailable(true);
        spaceRequest.setStatus(Status.REJEITADO);

        // Salva a solicitação atualizada
        spaceRequestRepository.save(spaceRequest);

        // Cria um registro de rejeição
        ApprovalFlow approvalFlow = ApprovalFlow.builder()
                .request(spaceRequest)
                .approvedBy(approver)
                .decision(Status.REJEITADO)
                .decisionDate(LocalDateTime.now())
                .build();

        // Salva o registro de rejeição
        return approvalFlowRepository.save(approvalFlow);
    }
}
