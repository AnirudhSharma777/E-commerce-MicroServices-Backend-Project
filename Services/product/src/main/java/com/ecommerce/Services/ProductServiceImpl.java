package com.ecommerce.Services;

import com.ecommerce.Dto.ProductPurchaseRequest;
import com.ecommerce.Dto.ProductRequest;
import com.ecommerce.Entities.Product;
import com.ecommerce.Exception.ProductPurchaseException;
import com.ecommerce.Repositories.ProductRepository;
import com.ecommerce.ResponseDto.ProductPurchaseResponse;
import com.ecommerce.ResponseDto.ProductResponse;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Override
    public Integer createProduct(ProductRequest request) {
        var product = productMapper.toProduct(request);
        return productRepository.save(product).getId();
    }

    @Override
    public List<ProductPurchaseResponse> purchaseProduct(List<ProductPurchaseRequest> request) {
        var productIds = request
                .stream()
                .map(ProductPurchaseRequest::productId)
                .collect(Collectors.toList());
        
        var storedProducts = productRepository.findAllByIdInOrderById(productIds);

        if(productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exists");
        }

        var storedRequest = request
        .stream()
        .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
        .toList();

        var purchasedProduct = new ArrayList<ProductPurchaseResponse>();
        for(int i = 0; i < storedRequest.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = storedRequest.get(i);

            if(product.getAvailableQuantity() < productRequest.quantity()) {
                throw new ProductPurchaseException("Insufficient stock quantity for product with id " + productRequest.productId());
            }

            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(product);
            purchasedProduct.add(productMapper.toproductPurchaseResponse(product,productRequest.quantity()));
        }

        return purchasedProduct;
    }

    @Override
    public ProductResponse getProduct(Integer productId) {
        return productRepository.findById(productId)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID:: " + productId));
    }

    @Override
    public List<ProductResponse> findAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> findProductWithSorting(String field) {
        List<String> allowedFields = List.of("name", "price", "category");
        if (!allowedFields.contains(field)) {
            throw new IllegalArgumentException("Invalid sorting field: " + field);
        }

        return productRepository.findAll(Sort.by(Sort.Direction.ASC, field)).stream()
                .map(productMapper::toProductResponse).toList();
    }

    @Override
    public Page<ProductResponse> findProductByPagination(int page, int pageSize) {
        Page<Product> products = productRepository.findAll(PageRequest.of(page, pageSize));
        return products.map(productMapper::toProductResponse);
    }

}
