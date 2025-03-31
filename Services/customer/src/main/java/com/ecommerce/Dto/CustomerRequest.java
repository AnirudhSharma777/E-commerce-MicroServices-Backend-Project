package com.ecommerce.Dto;

import com.ecommerce.Entities.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,

        @NotNull(message = "Customer firstname is required")
        String firstname,

        @NotNull(message = "Customer lastname is required")
        String lastname,

        @NotNull(message = "Customer email is required")
        @Email(message = "Customer Email is not a valid email address")
        String email,
        Address address
) {
}
