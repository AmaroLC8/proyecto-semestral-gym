package com.grupito.usuarios_services.dto;

import com.grupito.usuarios_services.model.Usuario;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    @Email(message = "Correo inválido")
    @NotBlank(message = "El correo es obligatorio")
    private String correo;
    
    private String telefono;
    
    @NotBlank(message = "La membresía es obligatoria")
    private String tipoMembresia;

    public Usuario toModel() {
        return new Usuario(id, nombre, correo, telefono, tipoMembresia);
    }

    public static UsuarioDTO fromModel(Usuario u) {
        if (u == null) return null;
        return new UsuarioDTO(u.getId(), u.getNombre(), u.getCorreo(), u.getTelefono(), u.getTipoMembresia());
    }
}