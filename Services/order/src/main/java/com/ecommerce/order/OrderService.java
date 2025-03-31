package com.ecommerce.order;

import com.ecommerce.customer.CustomerClient;
import com.ecommerce.exception.BusinessException;
import com.ecommerce.orderline.OrderLineRequest;
import com.ecommerce.orderline.OrderLineService;
import com.ecommerce.product.ProductClient;
import com.ecommerce.product.PurchaseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;

    public Integer createOrder(OrderRequest request) {

        // check the customer --> openFeign
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No Customer exists with the provided Id: "+request.customerId()));

        // purchase the products --> product microservice (restTemplate)  eg -> we can also use openFeign
        this.productClient.purchaseProduct(request.products());

        // persist order
        var order = this.orderRepository.save(mapper.toOrder(request));

        // persist order lines
        for(PurchaseRequest purchaseRequest: request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        // start payment process

        // send the order confirmation --> notification microservice (kafka)


        return null;
    }
}
