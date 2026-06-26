package com.grupito.review_services.service;

import com.grupito.review_services.exception.BadRequestException;
import com.grupito.review_services.exception.ResourceNotFoundException;
import com.grupito.review_services.model.Review;
import com.grupito.review_services.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository repo;

    private static final int CALIFICACION_MINIMA = 1;
    private static final int CALIFICACION_MAXIMA = 5;
    private static final int MAX_REVIEWS_POR_PRODUCTO = 100;
    private static final int LONGITUD_MINIMA_COMENTARIO = 10;

    public ReviewService(ReviewRepository repo) {
        this.repo = repo;
    }

    public List<Review> listar() {
        return repo.findAll();
    }

    public Review obtenerPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review no encontrada con id: " + id));
    }

    /**
     * Guarda una review aplicando las siguientes reglas de negocio:
     *
     * REGLA 1: La calificacion debe estar entre 1 y 5 estrellas.
     *          No se permiten calificaciones fuera de este rango ya que el sistema
     *          usa una escala estandar de 1 a 5 estrellas.
     *
     * REGLA 2: El comentario debe tener al menos 10 caracteres.
     *          Reviews muy cortas o vacias no son utiles para otros usuarios
     *          y no aportan valor al sistema de feedback.
     *
     * REGLA 3: Un producto no puede tener mas de 100 reviews en el sistema.
     *          Esto mantiene la consistencia de la base de datos y evita
     *          la saturacion del catalogo con opinion excesiva.
     */
    public Review guardar(Review review) {

        // REGLA 1: Calificacion entre 1 y 5
        if (review.getCalificacion() != null &&
                (review.getCalificacion() < CALIFICACION_MINIMA || review.getCalificacion() > CALIFICACION_MAXIMA)) {
            throw new BadRequestException(
                    "La calificacion debe estar entre " + CALIFICACION_MINIMA +
                    " y " + CALIFICACION_MAXIMA + " estrellas. Valor recibido: " + review.getCalificacion());
        }

        // REGLA 2: Longitud minima del comentario
        if (review.getComentario() == null || review.getComentario().trim().length() < LONGITUD_MINIMA_COMENTARIO) {
            throw new BadRequestException(
                    "El comentario de la review debe tener al menos " + LONGITUD_MINIMA_COMENTARIO + " caracteres.");
        }

        // REGLA 3: Limite de reviews por producto
        if (review.getIdProducto() != null) {
            long reviewsProducto = repo.findAll().stream()
                    .filter(r -> r.getIdProducto().equals(review.getIdProducto()))
                    .count();
            if (reviewsProducto >= MAX_REVIEWS_POR_PRODUCTO) {
                throw new BadRequestException(
                        "El producto con id " + review.getIdProducto() +
                        " ya tiene " + MAX_REVIEWS_POR_PRODUCTO + " reviews. No se pueden agregar mas.");
            }
        }

        return repo.save(review);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
