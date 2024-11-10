package com.ucsal.gestao_espaco_fisico.controller;

import com.ucsal.gestao_espaco_fisico.models.dto.SpaceDTO;
import com.ucsal.gestao_espaco_fisico.models.entitys.Space;
import com.ucsal.gestao_espaco_fisico.models.entitys.User;
import com.ucsal.gestao_espaco_fisico.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/spaces")
public class SpaceController {

    private final SpaceService spaceService;

    @Autowired
    public SpaceController(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    // Criar um novo espaço
    @PostMapping("/admin/create")
    public ResponseEntity<Space> createSpace(@RequestBody SpaceDTO spaceDTO) {
        Space createdSpace = spaceService.createSpace(spaceDTO);
        return ResponseEntity.ok(createdSpace);
    }

    // Atualizar um espaço existente
    @PutMapping("/admin/update/{id}")
    public ResponseEntity<Space> updateSpace(@PathVariable Long id, @RequestBody SpaceDTO spaceDTO) {
        Space updatedSpace = spaceService.updateSpace(id, spaceDTO);
        return ResponseEntity.ok(updatedSpace);
    }

    //Alterar a avaliabilidade do espaço
    @PutMapping("/admin/available/{id}")
    public ResponseEntity<Space> setAvailable(@PathVariable Long id, @RequestBody Space updatedSpace) {
        Space updated = spaceService.setAvailable(id, updatedSpace);
        //    Space updatedSpace = spaceService.setAvailable(id);
        return ResponseEntity.ok(updated);
    }

    // Atualizar a atividade do espaço
    @PutMapping("/admin/active/{id}")
    public ResponseEntity<Space> setActive(@PathVariable Long id, @RequestBody Space updatedSpace) {
        Space updated = spaceService.setActive(id, updatedSpace);
        return ResponseEntity.ok(updated);
    }

    // Remover um espaço
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Void> removeSpace(@PathVariable Long id) {
        spaceService.removeSpace(id);
        return ResponseEntity.noContent().build();
    }

    // Listar todos os espaços (opcional)
    @GetMapping("/all")
    public ResponseEntity<List<Space>> getAllSpaces() {
        List<Space> spaces = spaceService.getAllSpaces();
        return ResponseEntity.ok(spaces);
    }

    @GetMapping("/admin/getSpaceById/{spaceId}")
    public ResponseEntity<Space> getSpaceById(@PathVariable Long spaceId) {
        Optional<Space> space = spaceService.getSpaceById(spaceId);
        return space.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
