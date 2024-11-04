package com.musubi.teammusubi.domain.customer.controller;

import com.musubi.teammusubi.common.entity.Member;
import com.musubi.teammusubi.common.entity.Review;
import com.musubi.teammusubi.domain.customer.dto.ReviewRequest;
import com.musubi.teammusubi.domain.customer.dto.ReviewResponse;
import com.musubi.teammusubi.domain.customer.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/stores/{storeId}/reviews")
    public ResponseEntity<ReviewResponse> submit(@PathVariable Long storeId,
                                                 @RequestBody ReviewRequest req,
                                                 @AuthenticationPrincipal MemberDetailsImpl memberDetails) {

        Long memberId = memberDetails.getMember().getNickname();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reviewService.submit(storeId, req, memberId));

    }

    @GetMapping("/stores/{storeId}/reviews")
    public ResponseEntity<List<ReviewResponse>> findAll(@PathVariable Long storeId,
                                                        @RequestParam(required = false, defaultValue = "1") int page,
                                                        @RequestParam(required = false, defaultValue = "10") int size,
                                                        @RequestParam(required = false, defaultValue = "createdAt") String criteria {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.findAll(storeId, page, size, criteria));

    }


}
