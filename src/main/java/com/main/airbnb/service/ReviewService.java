package com.main.airbnb.service;

import com.main.airbnb.entity.Review;
import com.main.airbnb.entity.User;
import com.main.airbnb.payload.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto addReview(ReviewDto dto, User user, String propertyId);

    ReviewDto updateReview(ReviewDto dto, User user, String propertyId);


    String deleteReview(String reviewId);

    List<ReviewDto> getReviewByUser(User user);
}
