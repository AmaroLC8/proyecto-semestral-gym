package com.grupito.review_services.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupito.review_services.dto.ReviewDTO;
import com.grupito.review_services.service.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

	private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

	private final ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> crearReview(@RequestBody ReviewDTO reviewDto) {
		logger.info("Solicitud para crear review de: {}", reviewDto.getNombre());
		ReviewDTO creada = reviewService.crearReview(reviewDto);
		Map<String, Object> resp = new HashMap<>();
		resp.put("mensaje", "mensaje entregado");
		resp.put("review", creada);
		return ResponseEntity.ok(resp);
	}

	@GetMapping
	public ResponseEntity<List<ReviewDTO>> listarReviews() {
		List<ReviewDTO> lista = reviewService.listarReviews();
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/{id}/exists")
	public ResponseEntity<Boolean> existeReview(@PathVariable Long id) {
		return ResponseEntity.ok(reviewService.existePorId(id));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ReviewDTO> obtenerReview(@PathVariable Long id) {
		ReviewDTO dto = reviewService.obtenerPorId(id);
		if (dto != null) return ResponseEntity.ok(dto);
		return ResponseEntity.notFound().build();
	}
}
