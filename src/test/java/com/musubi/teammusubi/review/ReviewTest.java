package com.musubi.teammusubi.review;

import com.musubi.teammusubi.common.exception.ResponseException;
import com.musubi.teammusubi.domain.customer.dto.ReviewRequest;
import com.musubi.teammusubi.domain.customer.dto.ReviewResponse;
import com.musubi.teammusubi.domain.customer.dto.ReviewResponsePage;
import com.musubi.teammusubi.domain.customer.service.ReviewService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ReviewTest {

    private final ReviewService reviewService;

    @Autowired
    public ReviewTest(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Test
    @DisplayName("리뷰등록 - 완료상태 주문")
    @Disabled
    void test1() {

        //Given
        Long storeId = 1L;
        Long deliveryId = 2L;
        Integer score = 4;
        String comment = "맛있게 잘 먹었습니다.";
        String memberNickname = "눌눌";

        ReviewRequest req = new ReviewRequest();
        req.setScore(score);
        req.setComment(comment);

        //When
        ReviewResponse response = reviewService.submit(storeId, deliveryId, req, memberNickname);

        //Then
        assertEquals(deliveryId, response.getDeliveryId());

    }

    @Test
    @DisplayName("리뷰등록 - 진행중인 주문")
    @Disabled
    void test2() {

        //Given
        Long storeId = 1L;
        Long deliveryId = 1L;
        Integer score = 5;
        String comment = "맛있게 잘 먹었습니다.";
        String memberNickname = "눌눌";

        ReviewRequest req = new ReviewRequest();
        req.setScore(score);
        req.setComment(comment);

        //When & Then
        ResponseException exception = assertThrows(ResponseException.class, () ->
                reviewService.submit(storeId, deliveryId, req, memberNickname));

        assertEquals("배달 완료 상태 주문이 존재하지 않습니다.", exception.getMessage());


    }

    @Test
    @DisplayName("별점 별 리뷰 조회")
    @Disabled
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

        //When
        ReviewResponsePage responsePage = reviewService.findByScore(storeId, score, page, size, criteria);

        //Then
        assertEquals(1L, responsePage.getTotalElements());


    }

}
