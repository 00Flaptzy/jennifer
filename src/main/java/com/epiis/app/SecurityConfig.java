package com.epiis.app;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // 🔥 HABILITAR CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // 🔥 DESACTIVAR CSRF (API REST)
            .csrf(csrf -> csrf.disable())

            // 🔥 PERMITIR TODO (backend libre, JWT manual)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            );

        return http.build();
    }

    // 🔥 CORS PARA PRODUCCIÓN (ANGULAR + RENDER)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        // 🔥 PERMITIR CUALQUIER ORIGEN (Render + Angular prod)
        config.setAllowedOrigins(List.of("*"));

        // 🔥 MÉTODOS PERMITIDOS
        config.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"
        ));

        // 🔥 HEADERS PERMITIDOS
        config.setAllowedHeaders(List.of("*"));

        // 🔥 IMPORTANTE: con "*" debe ser FALSE
        config.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
