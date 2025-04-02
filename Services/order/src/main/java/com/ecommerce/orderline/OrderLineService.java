package com.ecommerce.orderline;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;

    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {

        var order = mapper.toOrderLine(orderLineRequest);
        return repository.save(order).getId();
    }

    public List<OrderLineResponse> findOrderLinesByOrderId(Integer id) {
        return repository.findAllByOrderId(id).stream().map(mapper::toOrderLineResponse).collect(Collectors.toList());
    }
}
