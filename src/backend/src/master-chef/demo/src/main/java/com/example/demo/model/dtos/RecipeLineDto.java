package com.example.demo.model.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RecipeLineDto {

    @NotBlank
    private String ingredient;
    @NotNull
    private Double amount;
    @NotBlank
    private String unit;

}
