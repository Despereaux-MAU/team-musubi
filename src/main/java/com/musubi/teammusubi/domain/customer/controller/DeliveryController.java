package com.musubi.teammusubi.domain.customer.controller;

import com.musubi.teammusubi.common.Security.MemberDetailsImpl;
import com.musubi.teammusubi.common.entity.Delivery;
import com.musubi.teammusubi.domain.customer.dto.DeliveryResponse;
import com.musubi.teammusubi.domain.customer.dto.OrderDeliveryRequest;
import com.musubi.teammusubi.domain.customer.service.DeliveryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<DeliveryResponse> orderDelivery(
            HttpServletRequest httpReq,
            @AuthenticationPrincipal MemberDetailsImpl memberDetails,
            @RequestParam Long storeId,
            @RequestParam Long menuId,
            @RequestBody OrderDeliveryRequest req
    ) {
        long memberId = memberDetails.getMember().getId();
        Delivery baseDelivery = req.toBaseDelivery();
        Delivery delivery = deliveryService.orderDelivery(memberId, storeId, menuId, baseDelivery);
        URI uri = URI.create(httpReq.getContextPath() + "/api/deliveries/" + delivery.getId());

        return ResponseEntity.created(uri).body(new DeliveryResponse(delivery));
    }

    @GetMapping
    public ResponseEntity<List<DeliveryResponse>> readAllDeliveries() {
        List<Delivery> deliveries = deliveryService.readAllDeliveries();
        List<DeliveryResponse> res = deliveries.stream().map(DeliveryResponse::new).toList();
        return ResponseEntity.ok(res);
    }
}
