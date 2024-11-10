package com.ucsal.gestao_espaco_fisico.models.dto;

import com.ucsal.gestao_espaco_fisico.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private String username;
    private String password;
    private String email;
    private Role role;
}
