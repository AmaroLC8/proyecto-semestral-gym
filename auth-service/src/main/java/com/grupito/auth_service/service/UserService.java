package com.grupito.auth_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.grupito.auth_service.exception.BadRequestException;
import com.grupito.auth_service.exception.ResourceNotFoundException;
import com.grupito.auth_service.model.User;
import com.grupito.auth_service.repository.UserRepository;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final HashService hashService;

    public UserService(UserRepository userRepository, JwtService jwtService, HashService hashService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.hashService = hashService;
    }

    // ── Autenticación ─────────────────────────────────────────────────────────

    /**
     * Valida credenciales y retorna un JWT. Lanza BadRequestException si fallan.
     */
    public String login(String email, String password) {
        logger.info("Intento de login para email: {}", email);
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new BadRequestException("Credenciales inválidas: email no encontrado.");
        }

        String hashedInput = hashService.sha1(password);
        if (!hashedInput.equals(user.getPassword())) {
            throw new BadRequestException("Credenciales inválidas: contraseña incorrecta.");
        }

        logger.info("Login exitoso para: {}", email);
        return jwtService.generateToken(email);
    }

    /**
     * Registra un nuevo usuario. Lanza BadRequestException si el email ya existe.
     */
    public User register(String email, String password) {
        logger.info("Registro de nuevo usuario: {}", email);
        User existing = userRepository.findByEmail(email);
        if (existing != null) {
            throw new BadRequestException("El email '" + email + "' ya está registrado.");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(hashService.sha1(password));
        user.setRole("USER");

        return userRepository.save(user);
    }

    // ── Consultas ─────────────────────────────────────────────────────────────

    public List<User> listar() {
        logger.info("Listando todos los usuarios");
        return userRepository.findAll();
    }

    public User obtenerPorId(Long id) {
        logger.info("Buscando usuario con id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario con id " + id + " no encontrado."));
    }

    public String getRole(String email) {
        User user = userRepository.findByEmail(email);
        return user != null ? user.getRole() : null;
    }
}
