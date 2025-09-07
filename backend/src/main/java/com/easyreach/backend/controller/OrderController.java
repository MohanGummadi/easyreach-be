package com.easyreach.backend.controller;

import com.easyreach.backend.entity.Order;
import com.easyreach.backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;

    @GetMapping
    public List<Order> list() {
        return orderRepository.findAll();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> find(@PathVariable String orderId) {
        return orderRepository.findByOrderIdIgnoreCase(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{orderId}/next-trip")
    public ResponseEntity<Map<String, Integer>> nextTrip(@PathVariable String orderId) {
        return orderRepository.findByOrderIdIgnoreCase(orderId)
                .map(o -> ResponseEntity.ok(Map.of("nextTripNo", (o.getTripNo() == null ? 0 : o.getTripNo()) + 1)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Order create(@RequestBody Order order) {
        return orderRepository.save(order);
    }
}
