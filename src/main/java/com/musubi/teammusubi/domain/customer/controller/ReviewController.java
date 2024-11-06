package com.musubi.teammusubi.domain.customer.controller;

import com.musubi.teammusubi.common.exception.ResponseException;
import com.musubi.teammusubi.common.security.MemberDetailsImpl;
import com.musubi.teammusubi.domain.customer.dto.ReviewRequest;
import com.musubi.teammusubi.domain.customer.dto.ReviewResponse;
import com.musubi.teammusubi.domain.customer.dto.ReviewResponsePage;
import com.musubi.teammusubi.domain.customer.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/stores/{storeId}/deliveries/{deliveryId}/reviews")
    public ResponseEntity<ReviewResponse> submit(@PathVariable Long storeId,
                                                 @PathVariable Long deliveryId,
                                                 @RequestBody ReviewRequest req,
                                                 @AuthenticationPrincipal MemberDetailsImpl memberDetails) {

        String memberNickname = memberDetails.getMember().getNickname();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reviewService.submit(storeId, deliveryId, req, memberNickname));

    }

    @GetMapping("/stores/{storeId}/reviews")
    public ResponseEntity<ReviewResponsePage> findAll(@PathVariable Long storeId,
                                                      @RequestParam(required = false, defaultValue = "1") int page,
                                                      @RequestParam(required = false, defaultValue = "10") int size,
                                                      @RequestParam(required = false, defaultValue = "createdAt") String criteria) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.findAll(storeId, page, size, criteria));

    }

    @GetMapping("/stores/{storeId}/score/reviews")
    public ResponseEntity<ReviewResponsePage> findByScore(@PathVariable Long storeId,
                                                          @RequestParam Integer score,
                                                          @RequestParam(required = false, defaultValue = "1") int page,
                                                          @RequestParam(required = false, defaultValue = "10") int size,
                                                          @RequestParam(required = false, defaultValue = "createdAt") String criteria) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.findByScore(storeId, score, page, size, criteria));

    }



}
