package com.ucsal.gestao_espaco_fisico.service;

import com.ucsal.gestao_espaco_fisico.models.entitys.User;
import com.ucsal.gestao_espaco_fisico.models.enums.Role;
import com.ucsal.gestao_espaco_fisico.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Atualiza um usuário existente
    @Transactional
    public User updateUser(Long userId, User updatedUser) {
        // Busca o usuário existente
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Atualiza os campos permitidos
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setRole(updatedUser.getRole());
        user.setIsActive(updatedUser.getIsActive());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    // Desativa um usuário
    @Transactional
    public void deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setIsActive(false);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    // Ativa um usuário
    @Transactional
    public void activateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setIsActive(true);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    // Busca todos os usuários
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Busca um usuário por ID
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

}
