package com.grupito.review_services;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.grupito.review_services.exception.BadRequestException;
import com.grupito.review_services.exception.ResourceNotFoundException;
import com.grupito.review_services.model.Review;
import com.grupito.review_services.repository.ReviewRepository;
import com.grupito.review_services.service.ReviewService;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    // ============================================================
    // TESTS CRUD BASICOS
    // ============================================================

    @Test
    public void testGuardarReview_Exitoso() {
        Review review = Review.builder()
                .idProducto(1L)
                .calificacion(5)
                .comentario("Excelente bicicleta estatica, muy comoda y silenciosa")
                .build();

        when(reviewRepository.findAll()).thenReturn(Collections.emptyList());
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review resultado = reviewService.guardar(review);

        assertNotNull(resultado);
        assertEquals(5, resultado.getCalificacion());
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    public void testListarReviews() {
        Review r1 = Review.builder().id(1L).idProducto(1L).calificacion(4).comentario("Muy buena calidad de construccion").build();
        Review r2 = Review.builder().id(2L).idProducto(2L).calificacion(3).comentario("Regular, esperaba un poco mas de durabilidad").build();
        when(reviewRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<Review> resultado = reviewService.listar();

        assertEquals(2, resultado.size());
    }

    @Test
    public void testObtenerPorId_Existente() {
        Long id = 1L;
        Review review = Review.builder().id(id).idProducto(1L).calificacion(5).comentario("Producto de alta calidad, lo recomiendo").build();
        when(reviewRepository.findById(id)).thenReturn(Optional.of(review));

        Review resultado = reviewService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals(5, resultado.getCalificacion());
    }

    @Test
    public void testObtenerPorId_NoEncontrado() {
        Long id = 99L;
        when(reviewRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reviewService.obtenerPorId(id));
    }

    @Test
    public void testEliminarReview() {
        Long id = 1L;
        doNothing().when(reviewRepository).deleteById(id);

        reviewService.eliminar(id);

        verify(reviewRepository, times(1)).deleteById(id);
    }

    // ============================================================
    // TESTS REGLAS DE NEGOCIO
    // ============================================================

    /**
     * REGLA 1: La calificacion debe estar entre 1 y 5 estrellas.
     * Probamos con calificacion de 0 (por debajo del minimo).
     */
    @Test
    public void testRegla1_CalificacionCero_LanzaExcepcion() {
        Review review = Review.builder()
                .idProducto(1L)
                .calificacion(0)
                .comentario("Mala experiencia con este producto del gimnasio")
                .build();

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> reviewService.guardar(review));
        assertTrue(ex.getMessage().contains("calificacion debe estar entre"));
    }

    /**
     * REGLA 1 (borde superior): Calificacion mayor a 5.
     */
    @Test
    public void testRegla1_CalificacionMayorA5_LanzaExcepcion() {
        Review review = Review.builder()
                .idProducto(1L)
                .calificacion(10)
                .comentario("Este producto es absolutamente maravilloso y excepcional")
                .build();

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> reviewService.guardar(review));
        assertTrue(ex.getMessage().contains("calificacion debe estar entre"));
    }

    /**
     * REGLA 2: El comentario debe tener al menos 10 caracteres.
     */
    @Test
    public void testRegla2_ComentarioMuyCorto_LanzaExcepcion() {
        Review review = Review.builder()
                .idProducto(1L)
                .calificacion(3)
                .comentario("Regular")  // Menos de 10 caracteres
                .build();

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> reviewService.guardar(review));
        assertTrue(ex.getMessage().contains("al menos"));
    }

    /**
     * REGLA 3: Un producto no puede tener mas de 100 reviews.
     */
    @Test
    public void testRegla3_LimiteReviewsPorProducto_LanzaExcepcion() {
        Long productoId = 1L;

        // Simular 100 reviews para el mismo producto
        List<Review> reviewsExistentes = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> Review.builder()
                        .id((long) i)
                        .idProducto(productoId)
                        .calificacion(4)
                        .comentario("Comentario de review numero " + i)
                        .build())
                .collect(Collectors.toList());

        Review nuevaReview = Review.builder()
                .idProducto(productoId)
                .calificacion(5)
                .comentario("Este es mi comentario sobre el producto del gimnasio")
                .build();

        when(reviewRepository.findAll()).thenReturn(reviewsExistentes);

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> reviewService.guardar(nuevaReview));
        assertTrue(ex.getMessage().contains("100 reviews"));
    }
}