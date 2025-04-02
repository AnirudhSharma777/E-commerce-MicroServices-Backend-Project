package com.ecommerce.order;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<Integer> createOrder(
           @Valid @RequestBody OrderRequest request
    ){
        return ResponseEntity.ok(service.createOrder(request));
    }

  @GetMapping
  public ResponseEntity<List<OrderResponse>>  getListOfOrder() {
        return ResponseEntity.ok(service.findAllOrder());
  }

  @GetMapping("/{order-id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable("order-id") Integer id){
        return ResponseEntity.ok(service.getOrderById(id));
  }

}
