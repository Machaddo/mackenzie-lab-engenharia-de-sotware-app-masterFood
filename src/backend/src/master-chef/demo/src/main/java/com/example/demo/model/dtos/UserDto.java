package com.example.demo.model.dtos;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserDto {

    @JsonProperty(access = Access.READ_ONLY)
    private UUID id;

    @NotBlank
    private String login;

    @JsonProperty(access = Access.WRITE_ONLY)
    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @JsonProperty(access = Access.READ_ONLY)
    private List<RecipeDto> recipes;

    public UserDto(UUID id, String login, String name, List<RecipeDto> recipes) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.recipes = recipes;
    }
}
