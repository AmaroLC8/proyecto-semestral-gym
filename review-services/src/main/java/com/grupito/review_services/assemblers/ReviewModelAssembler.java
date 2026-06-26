package com.grupito.review_services.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.grupito.review_services.controller.ReviewController;
import com.grupito.review_services.dto.ReviewDTO;

@Component
public class ReviewModelAssembler
        implements RepresentationModelAssembler<ReviewDTO, EntityModel<ReviewDTO>> {

    @Override
    public EntityModel<ReviewDTO> toModel(ReviewDTO review) {
        return EntityModel.of(review,
                linkTo(methodOn(ReviewController.class)
                        .obtener(review.getId())).withSelfRel(),
                linkTo(methodOn(ReviewController.class)
                        .listar()).withRel("reviews"));
    }
}
