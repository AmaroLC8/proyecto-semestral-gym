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
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Pattern(regexp = "^[A-Za-záéíóúÁÉÍÓÚüÜñÑ ]+$", message = "El nombre solo puede contener letras y espacios")
    private String nombre;

    @Email(message = "El correo electronico no es valido")
    @NotBlank(message = "El correo es obligatorio")
    @Size(max = 150, message = "El correo no puede superar los 150 caracteres")
    private String correo;

    @Pattern(regexp = "^[0-9+\\-\\s]{7,15}$", message = "El telefono debe contener entre 7 y 15 digitos")
    private String telefono;

    @NotBlank(message = "La membresia es obligatoria")
    @Pattern(regexp = "^(BASICA|ESTANDAR|PREMIUM|VIP)$",
             message = "El tipo de membresia debe ser: BASICA, ESTANDAR, PREMIUM o VIP")
    private String tipoMembresia;

    public Usuario toModel() {
        return new Usuario(id, nombre, correo, telefono, tipoMembresia);
    }

    public static UsuarioDTO fromModel(Usuario u) {
        if (u == null) return null;
        return new UsuarioDTO(u.getId(), u.getNombre(), u.getCorreo(), u.getTelefono(), u.getTipoMembresia());
    }
}