package com.main.airbnb.service;
import com.main.airbnb.exception.*;
import com.main.airbnb.entity.Property;
import com.main.airbnb.entity.Review;
import com.main.airbnb.entity.User;
import com.main.airbnb.exception.PropertyNotFoundException;
import com.main.airbnb.exception.ReviewAlreadyExistsException;
import com.main.airbnb.payload.ReviewDto;
import com.main.airbnb.repository.PropertyRepository;
import com.main.airbnb.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private  ReviewRepository reviewRepository;

    @Autowired
    private PropertyRepository propertyRepository;



    @Override
    public ReviewDto addReview(ReviewDto dto, User user, String propertyId) {
         Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new PropertyNotFoundException("Property Not Found")
        );
         Optional<Review> opReview = reviewRepository.findByPropertyAndUser(property, user);
         if(opReview.isPresent()){
             throw new ReviewAlreadyExistsException("Review is Already Given");
         }
         Review review = reviewDtoToEntity(dto, user, property);
         Review savedReview = reviewRepository.save(review);
         ReviewDto reviewDto = reviewEntityToDto(savedReview);
        return reviewDto;
    }

    @Override
    public ReviewDto updateReview(ReviewDto dto, User user, String propertyId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new PropertyNotFoundException("Property Not Found")
        );
         Review review = reviewRepository.findByPropertyAndUser(property, user).get();
         review.setDescription(dto.getDescription());
         review.setRating(dto.getRating());
         Review updatdReview = reviewRepository.save(review);
         ReviewDto reviewDto = reviewEntityToDto(updatdReview);
        return reviewDto;
    }

    @Override
    public String deleteReview(String reviewId) {
         Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ReviewNotFoundException("Review is not present")
        );
         reviewRepository.deleteById(review.getId());
        return "Review  is deleted Successfully";
    }

    @Override
    public List<ReviewDto> getReviewByUser(User user) {
         List<Review> listOfReview = reviewRepository.findByUser(user);
         List<ReviewDto> listReviewDto = listOfReview.stream().map(review -> reviewEntityToDto(review)).collect(Collectors.toList());
        return listReviewDto;
    }


    private ReviewDto reviewEntityToDto(Review savedReview) {
        ReviewDto review = new ReviewDto();
        review.setId(savedReview.getId());
        review.setPropertyName(savedReview.getProperty().getPropertyName());
        review.setUserName(savedReview.getUser().getName());
        review.setRating(savedReview.getRating());
        review.setDescription(savedReview.getDescription());
        return review;
    }

    private Review reviewDtoToEntity(ReviewDto dto, User user, Property property) {
         Review review = new Review();
         String reviewId = UUID.randomUUID().toString();
         review.setId(reviewId);
         review.setProperty(property);
         review.setUser(user);
         review.setRating(dto.getRating());
         review.setDescription(dto.getDescription());
         return review;
    }
}
