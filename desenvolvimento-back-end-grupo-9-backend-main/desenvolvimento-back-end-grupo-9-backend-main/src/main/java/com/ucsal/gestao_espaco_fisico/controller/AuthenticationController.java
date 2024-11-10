package com.ucsal.gestao_espaco_fisico.controller;

import com.ucsal.gestao_espaco_fisico.config.TokenService;
import com.ucsal.gestao_espaco_fisico.models.dto.AuthenticationDTO;
import com.ucsal.gestao_espaco_fisico.models.dto.LoginResponseDTO;
import com.ucsal.gestao_espaco_fisico.models.dto.RegisterDTO;
import com.ucsal.gestao_espaco_fisico.models.entitys.User;
import com.ucsal.gestao_espaco_fisico.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if (this.userRepository.findByUsername(data.getUsername()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        User user = User.builder()
                .username(data.getUsername())
                .email(data.getEmail())
                .password(encryptedPassword)
                .role(data.getRole())
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        this.userRepository.save(user);

        return ResponseEntity.ok().build();
    }
}
