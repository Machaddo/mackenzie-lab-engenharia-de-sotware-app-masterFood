package com.example.demo.data.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipe_line")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeLine {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "recipe_line_id")
    private UUID recipeLineId;
    private String ingredient;
    private Double amount;
    private String unit;
    @ManyToOne()
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    public RecipeLine(String ingredient, Double amount, String unit) {
        this.ingredient = ingredient;
        this.amount = amount;
        this.unit = unit;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

}
