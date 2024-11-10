package com.ucsal.gestao_espaco_fisico.controller;

import com.ucsal.gestao_espaco_fisico.models.dto.SpaceRequestDTO;
import com.ucsal.gestao_espaco_fisico.models.entitys.ApprovalFlow;
import com.ucsal.gestao_espaco_fisico.models.entitys.SpaceRequest;
import com.ucsal.gestao_espaco_fisico.models.entitys.User;
import com.ucsal.gestao_espaco_fisico.service.SpaceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spacerequests")
public class SpaceRequestController {

    private final SpaceRequestService spaceRequestService;

    @Autowired
    public SpaceRequestController(SpaceRequestService spaceRequestService) {
        this.spaceRequestService = spaceRequestService;
    }

    // Listar todas as solicitações de espaço
    @GetMapping("/manager/getAllSpaceRequests")
    public ResponseEntity<List<SpaceRequest>> getAllSpaceRequests() {
        List<SpaceRequest> spaceRequests = spaceRequestService.getAllSpaceRequests();
        return ResponseEntity.ok(spaceRequests);
    }

    @GetMapping("/manager/getAllAprovaFlows")
    public  ResponseEntity<List<ApprovalFlow>> getAllAprovaFlows() {
        List<ApprovalFlow> approvalFlows = spaceRequestService.getAllAprovaFlows();
        return ResponseEntity.ok(approvalFlows);
    }

    @PostMapping("/teacher/createSpaceRequest")
    public ResponseEntity<SpaceRequest> createSpaceRequest(
            @RequestBody SpaceRequestDTO spaceRequestDTO,
            @AuthenticationPrincipal User user // Supondo que a autenticação traga o User autenticado
    ) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }



        SpaceRequest createdRequest = spaceRequestService.createSpaceRequest(spaceRequestDTO, user);
        return ResponseEntity.ok(createdRequest);
    }

    // Aprovar uma solicitação de espaço
    @PostMapping("/manager/approve/{requestId}")
    public ResponseEntity<ApprovalFlow> approveSpaceRequest(
            @PathVariable Long requestId,
            @AuthenticationPrincipal User approver // Supondo que a autenticação traga o User autenticado
    ) {
        if (approver == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        ApprovalFlow approvalFlow = spaceRequestService.approveRequest(requestId, approver);
        return ResponseEntity.ok(approvalFlow);
    }

    // Rejeitar uma solicitação de espaço
    @PostMapping("/manager/reject/{requestId}")
    public ResponseEntity<ApprovalFlow> rejectSpaceRequest(
            @PathVariable Long requestId,
            @AuthenticationPrincipal User approver // Supondo que a autenticação traga o User autenticado
    ) {
        ApprovalFlow approvalFlow = spaceRequestService.rejectRequest(requestId, approver);
        return ResponseEntity.ok(approvalFlow);
    }
}
