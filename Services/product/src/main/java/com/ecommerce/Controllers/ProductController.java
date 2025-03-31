package com.ecommerce.Controllers;

import com.ecommerce.Dto.ProductPurchaseRequest;
import com.ecommerce.Dto.ProductRequest;
import com.ecommerce.ResponseDto.ProductPurchaseResponse;
import com.ecommerce.ResponseDto.ProductResponse;
import com.ecommerce.Services.ProductServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl service;

    @PostMapping
    public ResponseEntity<?> newProduct(@RequestBody @Valid ProductRequest request) {
        return ResponseEntity.ok(service.createProduct(request));
    }


    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProduct(@RequestBody List<ProductPurchaseRequest> request) {
        return ResponseEntity.ok(service.purchaseProduct(request));
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("product_id") Integer id) {
        return ResponseEntity.ok(service.getProduct(id));
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(service.findAllProducts());
    }


    @GetMapping("/paginate")
    public ResponseEntity<Page<ProductResponse>> getPaginatedProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<ProductResponse> paginatedProducts = service.findProductByPagination(page, pageSize);
        return ResponseEntity.ok(paginatedProducts);
    }

    
    @GetMapping("/sort")
    public ResponseEntity<List<ProductResponse>> getSortedProducts(
            @RequestParam(defaultValue = "name") String field) {
        List<ProductResponse> sortedProducts = service.findProductWithSorting(field);
        return ResponseEntity.ok(sortedProducts);
    }

}
