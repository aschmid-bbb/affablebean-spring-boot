package ch.bbbaden.webshop.controller.records;

import jakarta.validation.constraints.*;

public record Order (
        @NotNull @NotBlank String name,
        @NotNull @NotBlank @Email String email,
        @NotNull @NotBlank String phone,
        @NotNull @NotBlank String address,
        String cityRegion,
        @NotNull String ccnumber,
        String promocode){
}
