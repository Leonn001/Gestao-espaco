package com.ucsal.gestao_espaco_fisico.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // Permitir todas as origens. Use uma origem específica em produção.
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // URL do seu frontend
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
        corsConfiguration.setAllowedHeaders(Arrays.asList("*")); // Permitir todos os cabeçalhos

        // Adicione as configurações
        configurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(configurationSource);
    }
}
