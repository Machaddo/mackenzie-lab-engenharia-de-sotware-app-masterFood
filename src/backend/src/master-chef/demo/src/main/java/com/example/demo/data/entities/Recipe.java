package com.example.demo.data.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.demo.model.dtos.RecipeLineDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipe")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "recipe_id")
    private UUID recipeId;
    private String name;
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeLine> lines;

    public Recipe(String name, List<RecipeLineDto> recipesDto) {
        this.name = name;
        this.lines = new ArrayList<>();
        recipesDto.stream().forEach(recipeDto -> {
            RecipeLine line = new RecipeLine(recipeDto.getIngredient(), recipeDto.getAmount(), recipeDto.getUnit());
            line.setRecipe(this);
            lines.add(line);
        });
    }

    public void setUser(User user) {
        this.user = user;
    }
}
