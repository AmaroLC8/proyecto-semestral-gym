package com.grupito.review_services.service;

import com.grupito.review_services.exception.ResourceNotFoundException;
import com.grupito.review_services.model.Review;
import com.grupito.review_services.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository repo;
    public ReviewService(ReviewRepository repo) { this.repo = repo; }

    public List<Review> listar() { return repo.findAll(); }
    public Review obtenerPorId(Long id) { 
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review no encontrada con id: " + id)); 
    }
    public Review guardar(Review review) {
        if (review.getCalificacion() != null && (review.getCalificacion() < 1 || review.getCalificacion() > 5)) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }
        return repo.save(review);
    }
    public void eliminar(Long id) { repo.deleteById(id); }
}
