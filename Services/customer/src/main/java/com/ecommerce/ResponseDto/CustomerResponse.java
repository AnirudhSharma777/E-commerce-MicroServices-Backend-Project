package com.ecommerce.ResponseDto;

import com.ecommerce.Entities.Address;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email,
        Address address
) {
}
