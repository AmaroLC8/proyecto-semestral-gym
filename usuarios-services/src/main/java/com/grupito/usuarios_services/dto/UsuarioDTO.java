package com.grupito.usuarios_services.dto;

import com.grupito.usuarios_services.model.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String telefono;
    private String tipoMembresia;

    public Usuario toModel(){
        return new Usuario(id, nombre, correo, telefono, tipoMembresia);
    }
    public static UsuarioDTO fromModel (Usuario u){
        if (u == null) return null;
        return new UsuarioDTO(u.getId(), u.getNombre(), u.getCorreo(), u.getTelefono(), u.getTipoMembresia());
    }
}
