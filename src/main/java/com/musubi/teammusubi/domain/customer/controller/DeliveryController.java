package com.musubi.teammusubi.domain.customer.controller;

import com.musubi.teammusubi.common.entity.Delivery;
import com.musubi.teammusubi.common.security.MemberDetailsImpl;
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
import java.util.Map;
import java.util.stream.Collectors;

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
            @RequestParam String details,
            @RequestBody List<OrderDeliveryRequest> orderRequest
    ) {
        long memberId = memberDetails.getMember().getId();

        Map<Long,Integer> orderMap = reqListToMap(orderRequest);

        Delivery delivery = deliveryService.orderDelivery(details, memberId, storeId, orderMap);
        URI uri = URI.create(httpReq.getContextPath() + "/api/deliveries/" + delivery.getId());
        return ResponseEntity.created(uri).body(DeliveryResponse.allOf(delivery));
    }

    @GetMapping
    public ResponseEntity<List<DeliveryResponse>> readAllDeliveries(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        long memberId = memberDetails.getMember().getId();
        List<Delivery> deliveries = deliveryService.readAllDeliveries(memberId);
        List<DeliveryResponse> res = deliveries.stream().map(DeliveryResponse::of).toList();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponse> readDeliveryById(@AuthenticationPrincipal MemberDetailsImpl memberDetails, @PathVariable("id") Long id) {
        long memberId = memberDetails.getMember().getId();
        Delivery delivery = deliveryService.readDelivery(memberId, id);
        return ResponseEntity.ok(DeliveryResponse.allOf(delivery));
    }


    private Map<Long,Integer> reqListToMap(List<OrderDeliveryRequest> lis) {
        return lis.stream().collect(Collectors.toMap(
                OrderDeliveryRequest::getMenuId,
                OrderDeliveryRequest::getQuantity,
                Integer::sum // 같은 메뉴 id 가 여럿일 경우, 수량을 합침
        ));
    }
}
