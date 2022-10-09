package com.jonas.orderservice.controller;

import com.jonas.orderservice.dto.OrderRequestDto;
import com.jonas.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> placeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        Map<String, String> message = new HashMap<>();
        orderService.placeOrder(orderRequestDto);
        message.put("message", "Order Placed Successfully");
        return message;
    }
}