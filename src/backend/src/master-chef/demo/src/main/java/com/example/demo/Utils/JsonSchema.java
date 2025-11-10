package com.example.demo.Utils;

import java.util.List;
import java.util.Map;

public class JsonSchema {

    public static Map<String, Object> getRecipetSchema() {
        Map<String, Object> lineSchema = Map.of(
                "type", "object",
                "properties", Map.of(
                        "ingredient", Map.of("type", "string"),
                        "amount", Map.of("type", "number"),
                        "unit", Map.of("type", "string")),
                "required", List.of("ingredient", "unit"));

        Map<String, Object> linesSchema = Map.of(
                "type", "array",
                "items", lineSchema);

        Map<String, Object> recipeSchema = Map.of(
                "type", "object",
                "properties", Map.of(
                        "name", Map.of("type", "string"),
                        "lines", linesSchema),
                "required", List.of("name", "lines"));

        Map<String, Object> recipesSchema = Map.of(
                "type", "object",
                "properties", Map.of(
                        "recipes", Map.of(
                                "type", "array",
                                "items", recipeSchema)));

        Map<String, Object> schema = Map.of(
                "name", "recipe_schema",
                "schema", recipesSchema);

        return schema;
    }

    public static Map<String, Object> getFilterSchema(){
        Map<String, Object> filterSchema = Map.of(
                "name", "filter_schema",
                "schema", Map.of(
                        "type", "object",
                        "properties", Map.of(
                                "is_filtered", Map.of(
                                        "type", "boolean"
                                )
                        ),
                        "required", List.of("is_filtered"),
                        "additionalProperties", false
                )
        );
        return filterSchema;
    }
}
