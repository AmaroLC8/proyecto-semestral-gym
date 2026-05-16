package com.grupito.usuarios_services.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupito.usuarios_services.dto.UsuarioDTO;
import com.grupito.usuarios_services.model.Usuario;
import com.grupito.usuarios_services.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crearusuario(@RequestBody UsuarioDTO usuarioDto) {
        Usuario nuevoUsuario = usuarioService.guardar(usuarioDto.toModel());
        return ResponseEntity.ok(UsuarioDTO.fromModel(nuevoUsuario));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarClientes() {
        List<Usuario> clientes = usuarioService.listar();
        List<UsuarioDTO> dtos = clientes.stream().map(UsuarioDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existeCliente(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.existePorId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        if (usuario != null) {
            return ResponseEntity.ok(UsuarioDTO.fromModel(usuario));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDto) {
        Usuario usuarioExistente = usuarioService.obtenerPorId(id);
        if (usuarioExistente != null) {
            usuarioDto.setId(id);
            Usuario usuarioActualizado = usuarioService.actualizar(usuarioDto.toModel());
            return ResponseEntity.ok(UsuarioDTO.fromModel(usuarioActualizado));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (usuarioService.existePorId(id)) {
            usuarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
