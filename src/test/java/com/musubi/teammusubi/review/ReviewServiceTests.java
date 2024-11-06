package com.musubi.teammusubi.review;

import com.musubi.teammusubi.TeamMusubiApplication;
import com.musubi.teammusubi.common.exception.ResponseException;
import com.musubi.teammusubi.domain.customer.dto.ReviewRequest;
import com.musubi.teammusubi.domain.customer.dto.ReviewResponse;
import com.musubi.teammusubi.domain.customer.dto.ReviewResponsePage;
import com.musubi.teammusubi.domain.customer.repository.DeliveryRepository;
import com.musubi.teammusubi.domain.customer.repository.ReviewRepository;
import com.musubi.teammusubi.domain.customer.repository.StoreRepository;
import com.musubi.teammusubi.domain.customer.service.ReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ContextConfiguration(classes = TeamMusubiApplication.class)
public class ReviewServiceTests {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final DeliveryRepository deliveryRepository;

    @Autowired
    public ReviewServiceTests(ReviewRepository reviewRepository, StoreRepository storeRepository, DeliveryRepository deliveryRepository) {
        this.reviewRepository = reviewRepository;
        this.storeRepository = storeRepository;
        this.deliveryRepository = deliveryRepository;
    }

    @Test
    @DisplayName("리뷰등록 - 완료상태 주문")
    void test1() {

        //Given
        Long storeId = 1L;
        Long deliveryId = 1L;
        Integer score = 4;
        String comment = "맛있게 잘 먹었습니다.";
        String memberNickname = "눌눌";

        ReviewRequest req = new ReviewRequest();
        req.setScore(score);
        req.setComment(comment);

        ReviewService reviewService = new ReviewService(reviewRepository, storeRepository, deliveryRepository);

        //When
        ReviewResponse response = reviewService.submit(storeId, deliveryId, req, memberNickname);

        //Then
        assertEquals(deliveryId, response.getDeliveryId());

    }

    @Test
    @DisplayName("리뷰등록 - 진행중인 주문")
    void test2() {

        //Given
        Long storeId = 1L;
        Long deliveryId = 2L;
        Integer score = 5;
        String comment = "맛있게 잘 먹었습니다.";
        String memberNickname = "눌눌";

        ReviewRequest req = new ReviewRequest();
        req.setScore(score);
        req.setComment(comment);

        ReviewService reviewService = new ReviewService(reviewRepository, storeRepository, deliveryRepository);

        //When & Then
        ResponseException exception = assertThrows(ResponseException.class, () ->
                reviewService.submit(storeId, deliveryId, req, memberNickname));

        assertEquals("배달 완료 상태 주문이 존재하지 않습니다.", exception.getMessage());


    }

    @Test
    @DisplayName("별점 별 리뷰 조회")
    void test3() {

        //Given
        Long storeId = 1L;
        Integer score = 4;
        int page = 1;
        int size = 10;
        String criteria = "createdAt";
        String comment = "맛있게 잘 먹었습니다.";

        ReviewRequest req = new ReviewRequest();
        req.setScore(score);
        req.setComment(comment);

        ReviewService reviewService = new ReviewService(reviewRepository, storeRepository, deliveryRepository);

        //When
        ReviewResponsePage responsePage = reviewService.findByScore(storeId, score, page, size, criteria);

        //Then
        assertEquals(1L, responsePage.getTotalElements());

    }

}
