package com.example.wowshop.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank @Size(max = 50) String name,
        @NotBlank @Email @Size(max = 120) String email,
        @NotBlank @Size(min = 8, max = 60) String password,
        @NotBlank @Pattern(regexp = "^[0-9-]{10,20}$") String phone,
        @NotBlank @Size(max = 10) String zipcode,
        @NotBlank @Size(max = 180) String address,
        @AssertTrue boolean termsAgreed,
        boolean marketingAgreed
) {
}
