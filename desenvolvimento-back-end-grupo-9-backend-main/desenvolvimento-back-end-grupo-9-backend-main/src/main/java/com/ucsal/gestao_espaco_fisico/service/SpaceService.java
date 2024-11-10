package com.ucsal.gestao_espaco_fisico.service;

import com.ucsal.gestao_espaco_fisico.models.dto.SpaceDTO;
import com.ucsal.gestao_espaco_fisico.models.entitys.ApprovalFlow;
import com.ucsal.gestao_espaco_fisico.models.entitys.Space;
import com.ucsal.gestao_espaco_fisico.repository.ApprovalFlowRepository;
import com.ucsal.gestao_espaco_fisico.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpaceService {

    private final SpaceRepository spaceRepository;
    private final ApprovalFlowRepository approvalFlowRepository;

    @Autowired
    public SpaceService(SpaceRepository spaceRepository, ApprovalFlowRepository approvalFlowRepository) {
        this.spaceRepository = spaceRepository;
        this.approvalFlowRepository = approvalFlowRepository;
    }

    // Método para criar um novo espaço
    public Space createSpace(SpaceDTO spaceDTO) {
        Space space = Space.builder()
                .name(spaceDTO.getName())
                .location(spaceDTO.getLocation())
                .capacity(spaceDTO.getCapacity())
                .type(spaceDTO.getType())
                .isAvailable(true)
                .isActive(true)
                .resources(spaceDTO.getResources())
                .build();

        return spaceRepository.save(space);
    }

    // Método para atualizar um espaço existente
    public Space updateSpace(Long id, SpaceDTO spaceDTO) {
        Space space = spaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Space not found"));

        space.setName(spaceDTO.getName());
        space.setLocation(spaceDTO.getLocation());
        space.setCapacity(spaceDTO.getCapacity());
        space.setType(spaceDTO.getType());
        space.setResources(spaceDTO.getResources());

        return spaceRepository.save(space);
    }

    public Space setAvailable(Long id, Space updatedSpace) {
        Space space = spaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Space not found"));
        space.setIsAvailable(updatedSpace.getIsAvailable());
        return spaceRepository.save(space);
    }

    public Space setActive(Long id, Space updatedSpace) {
        Space space = spaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Space not found"));
        space.setIsActive(updatedSpace.getIsActive());
        return spaceRepository.save(space);
    }

    // Método para remover um espaço
    public void removeSpace(Long id) {
        Optional<Space> space = spaceRepository.findById(id);

        if (space.isPresent()) {
            spaceRepository.delete(space.get());
        } else {
            throw new RuntimeException("Space not found");
        }
    }

    public List<Space> getAllSpaces() {
        return spaceRepository.findAll();
    }

    public Optional<Space> getSpaceById(Long spaceId) {
        return spaceRepository.findById(spaceId);
    }
}
