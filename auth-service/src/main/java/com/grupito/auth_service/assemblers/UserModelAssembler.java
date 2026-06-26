package com.grupito.auth_service.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.grupito.auth_service.controller.AuthController;
import com.grupito.auth_service.dto.UserDTO;

@Component
public class UserModelAssembler
        implements RepresentationModelAssembler<UserDTO, EntityModel<UserDTO>> {

    @Override
    public EntityModel<UserDTO> toModel(UserDTO user) {
        return EntityModel.of(user,
                linkTo(methodOn(AuthController.class)
                        .obtenerUsuario(user.getId())).withSelfRel(),
                linkTo(methodOn(AuthController.class)
                        .listarUsuarios()).withRel("usuarios"));
    }
}
