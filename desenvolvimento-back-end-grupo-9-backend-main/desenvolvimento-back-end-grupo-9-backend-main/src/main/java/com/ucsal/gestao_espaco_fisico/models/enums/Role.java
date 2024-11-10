package com.ucsal.gestao_espaco_fisico.models.enums;

import com.ucsal.gestao_espaco_fisico.models.entitys.User;

public enum Role {
    ADMINISTRATOR("admin"),
    MANAGER("manager"),
    TEACHER("teacher");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
