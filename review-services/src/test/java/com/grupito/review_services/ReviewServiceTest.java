package com.grupito.review_services;

import com.grupito.review_services.exception.ResourceNotFoundException;
import com.grupito.review_services.model.Review;
import com.grupito.review_services.repository.ReviewRepository;
import com.grupito.review_services.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    public void testGuardarReview_Exitoso() {
        Review review = Review.builder()
                .idProducto(100L)
                .calificacion(5)
                .comentario("Excelente producto")
                .build();

        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review resultado = reviewService.guardar(review);

        assertNotNull(resultado);
        assertEquals(5, resultado.getCalificacion());
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    public void testListarReviews() {
        Review r1 = Review.builder().id(1L).calificacion(5).build();
        Review r2 = Review.builder().id(2L).calificacion(4).build();
        when(reviewRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<Review> resultado = reviewService.listar();

        assertEquals(2, resultado.size());
    }

    @Test
    public void testObtenerPorId_Existente() {
        Long id = 1L;
        Review review = Review.builder().id(id).calificacion(5).build();
        when(reviewRepository.findById(id)).thenReturn(Optional.of(review));

        Review resultado = reviewService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals(5, resultado.getCalificacion());
    }

    @Test
    public void testObtenerPorId_NoEncontrado() {
        Long id = 99L;
        when(reviewRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> reviewService.obtenerPorId(id));
        assertTrue(exception.getMessage().contains("Review no encontrada"));
    }

    @Test
    public void testEliminarReview() {
        Long id = 1L;
        doNothing().when(reviewRepository).deleteById(id);

        reviewService.eliminar(id);

        verify(reviewRepository, times(1)).deleteById(id);
    }
}