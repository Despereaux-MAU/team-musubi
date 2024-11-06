package com.musubi.teammusubi.domain.customer.service;

import com.musubi.teammusubi.common.entity.Review;
import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.common.enums.DeliveryStatus;
import com.musubi.teammusubi.common.exception.ExceptionType;
import com.musubi.teammusubi.common.exception.ResponseException;
import com.musubi.teammusubi.domain.customer.dto.ReviewRequest;
import com.musubi.teammusubi.domain.customer.dto.ReviewResponse;
import com.musubi.teammusubi.domain.customer.dto.ReviewResponsePage;
import com.musubi.teammusubi.domain.customer.repository.DeliveryRepository;
import com.musubi.teammusubi.domain.customer.repository.ReviewRepository;
import com.musubi.teammusubi.domain.customer.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final DeliveryRepository deliveryRepository;


    public ReviewResponse submit(Long storeId, Long deliveryId, ReviewRequest req, String memberNickname) {

        Store store = validStore(storeId);

        deliveryRepository.findByIdAndStatus(deliveryId, DeliveryStatus.COMPLETED).orElseThrow(() ->
                new ResponseException(ExceptionType.COMPLETED_DELIVERY_NOT_FOUND));

        Review review = reviewRepository.save(Review.from(store, deliveryId, req, memberNickname));

        return ReviewResponse.of(review);

    }

    public ReviewResponsePage findAll(Long storeId, int page, int size, String criteria) {

        validStore(storeId);

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, criteria));
        Page<Review> reviews = reviewRepository.findByStoreId(storeId, pageable);

        return new ReviewResponsePage(reviews);

    }

    public ReviewResponsePage findByScore(Long storeId, Integer score, int page, int size, String criteria) {

        validStore(storeId);

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, criteria));
        Page<Review> reviews = reviewRepository.findByScore(score, pageable);

        return new ReviewResponsePage(reviews);
    }

    public Store validStore(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(() ->
                new ResponseException(ExceptionType.STORE_NOT_FOUND));
    }


}
