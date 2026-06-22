package com.grupito.review_services.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.grupito.review_services.dto.ReviewDTO;
import com.grupito.review_services.model.Review;
import com.grupito.review_services.repository.ReviewRepository;

@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;

	public ReviewService(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}

	public ReviewDTO crearReview(ReviewDTO dto) {
		Review r = dto.toModel();
		if (r.getFecha() == null) {
			r.setFecha(LocalDateTime.now());
		}
		Review saved = reviewRepository.save(r);
		return ReviewDTO.fromModel(saved);
	}

	public List<ReviewDTO> listarReviews() {
		return reviewRepository.findAll().stream()
				.map(ReviewDTO::fromModel)
				.collect(Collectors.toList());
	}

	public boolean existePorId(Long id) {
		return reviewRepository.existsById(id);
	}

	public ReviewDTO obtenerPorId(Long id) {
		return reviewRepository.findById(id).map(ReviewDTO::fromModel).orElse(null);
	}
}
