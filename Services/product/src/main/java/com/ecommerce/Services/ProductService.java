package com.ecommerce.Services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ecommerce.Dto.ProductPurchaseRequest;
import com.ecommerce.Dto.ProductRequest;
import com.ecommerce.ResponseDto.ProductPurchaseResponse;
import com.ecommerce.ResponseDto.ProductResponse;

public interface ProductService {

    public Integer createProduct(ProductRequest request);
    public List<ProductPurchaseResponse> purchaseProduct(List<ProductPurchaseRequest> request);
    public ProductResponse getProduct(Integer productId);
    public List<ProductResponse> findAllProducts();
    public List<ProductResponse> findProductWithSorting(String field);
    Page<ProductResponse> findProductByPagination(int offset, int pageSize);
}
