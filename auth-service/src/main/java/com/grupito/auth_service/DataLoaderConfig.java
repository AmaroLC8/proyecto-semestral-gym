package com.grupito.auth_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.grupito.auth_service.model.User;
import com.grupito.auth_service.repository.UserRepository;
import com.grupito.auth_service.service.HashService;

/**
 * DataLoader — carga el 30% de datos programáticos.
 * El 70% restante es cargado por Liquibase (db.changelog.sql).
 */
@Configuration
public class DataLoaderConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataLoaderConfig.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, HashService hashService) {
        return args -> {
            // 30% de datos programáticos — 3 usuarios adicionales
            // Se usa findByEmail para no duplicar si ya existe
            crearSiNoExiste(userRepository, hashService,
                    "superadmin@gym.com", "123456", "ADMIN");
            crearSiNoExiste(userRepository, hashService,
                    "trainer.miguel@gym.com", "123456", "TRAINER");
            crearSiNoExiste(userRepository, hashService,
                    "miembro.carmen@gym.com", "123456", "USER");

            logger.info("DataLoader completado. Total usuarios: {}", userRepository.count());
        };
    }

    private void crearSiNoExiste(UserRepository repo, HashService hash,
                                  String email, String password, String role) {
        if (repo.findByEmail(email) == null) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(hash.sha1(password));
            user.setRole(role);
            repo.save(user);
            logger.info("Usuario creado por DataLoader: {} / Rol: {}", email, role);
        }
    }
}