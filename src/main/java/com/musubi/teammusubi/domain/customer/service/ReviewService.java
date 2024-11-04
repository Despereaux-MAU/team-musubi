package com.musubi.teammusubi.domain.customer.service;

import com.musubi.teammusubi.common.entity.Member;
import com.musubi.teammusubi.common.entity.Review;
import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.domain.customer.dto.ReviewRequest;
import com.musubi.teammusubi.domain.customer.dto.ReviewResponse;
import com.musubi.teammusubi.domain.customer.dto.ReviewResponsePage;
import com.musubi.teammusubi.domain.customer.repository.ReviewRepository;
import com.musubi.teammusubi.domain.customer.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    public ReviewResponse submit(Long storeId, ReviewRequest req, Member member) {

        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new NullPointerException("가게가 존재하지 않습니다."));

        Review review = reviewRepository.save(Review.from(store, req, member));

        return ReviewResponse.of(review);

    }

    public ReviewResponsePage findAll(Long storeId, int page, int size, String criteria) {

        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new NullPointerException("가게가 존재하지 않습니다."));

        Pageable pageable = PageRequest.of(page-1, size, Sort.by(Sort.Direction.DESC, criteria));


        reviewRepository.findByStoreId(storeId)


    }
}
