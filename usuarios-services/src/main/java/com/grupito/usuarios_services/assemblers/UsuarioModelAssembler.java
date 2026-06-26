package com.grupito.usuarios_services.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.grupito.usuarios_services.controller.UsuarioController;
import com.grupito.usuarios_services.dto.UsuarioDTO;

@Component
public class UsuarioModelAssembler
        implements RepresentationModelAssembler<UsuarioDTO, EntityModel<UsuarioDTO>> {

    @Override
    public EntityModel<UsuarioDTO> toModel(UsuarioDTO usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioController.class)
                        .obtener(usuario.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class)
                        .listar()).withRel("usuarios"));
    }
}
