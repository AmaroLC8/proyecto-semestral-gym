package com.grupito.auth_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.grupito.auth_service.model.User;
import com.grupito.auth_service.repository.UserRepository;
import com.grupito.auth_service.service.HashService;

@Configuration
public class DataLoaderConfig {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, HashService hashService) {
        return args -> {
            
            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setEmail("admin@gym.com");
                
                admin.setPassword(hashService.sha1("123456")); 
                userRepository.save(admin);
                System.out.println("✅ Usuario creado por defecto para Swagger/Docker: admin@gym.com / 123456");
            }
        };
    }
}