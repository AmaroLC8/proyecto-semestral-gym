package com.grupito.auth_service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.grupito.auth_service.assemblers.UserModelAssembler;
import com.grupito.auth_service.dto.AuthResponseDTO;
import com.grupito.auth_service.dto.LoginRequestDTO;
import com.grupito.auth_service.dto.RegisterRequestDTO;
import com.grupito.auth_service.dto.UserDTO;
import com.grupito.auth_service.model.User;
import com.grupito.auth_service.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;
    private final UserModelAssembler assembler;

    public AuthController(UserService userService, UserModelAssembler assembler) {
        this.userService = userService;
        this.assembler = assembler;
    }

    // ── POST /auth/login ──────────────────────────────────────────────────────
    @PostMapping("/login")
    public EntityModel<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        logger.info("POST /auth/login - email: {}", request.getEmail());
        User user = userService.login(request.getEmail(), request.getPassword());

        AuthResponseDTO dto = AuthResponseDTO.builder()
                .status("ok")
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        return EntityModel.of(dto,
                linkTo(methodOn(AuthController.class).listarUsuarios()).withRel("usuarios"));
    }

    // ── POST /auth/register ───────────────────────────────────────────────────
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        logger.info("POST /auth/register - email: {}", request.getEmail());
        User newUser = userService.register(request.getEmail(), request.getPassword());

        AuthResponseDTO dto = AuthResponseDTO.builder()
                .status("created")
                .email(newUser.getEmail())
                .role(newUser.getRole())
                .build();

        return EntityModel.of(dto,
                linkTo(methodOn(AuthController.class).listarUsuarios()).withRel("usuarios"));
    }

    // ── GET /auth/users ───────────────────────────────────────────────────────
    @GetMapping("/users")
    public CollectionModel<EntityModel<UserDTO>> listarUsuarios() {
        logger.info("GET /auth/users - Listando usuarios");
        List<EntityModel<UserDTO>> users = userService.listar().stream()
                .map(UserDTO::fromModel)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(users,
                linkTo(methodOn(AuthController.class).listarUsuarios()).withSelfRel());
    }

    // ── GET /auth/users/{id} ──────────────────────────────────────────────────
    @GetMapping("/users/{id}")
    public EntityModel<UserDTO> obtenerUsuario(@PathVariable Long id) {
        logger.info("GET /auth/users/{} - Obteniendo usuario", id);
        return assembler.toModel(UserDTO.fromModel(userService.obtenerPorId(id)));
    }
}