package com.grupito.review_services.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupito.review_services.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}