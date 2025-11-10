package com.example.demo.Utils;

import java.util.List;

import com.example.demo.model.exceptions.InvalidUserPromptException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParsing {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <E> List<E> parseRecipe(String json, Class<E> clas) throws Exception {
        JsonNode root = mapper.readTree(json);
        String content = root.path("choices")
                .get(0).path("message").path("content").asText();

        JsonNode recipes = mapper.readTree(content).path("recipes");

        if (recipes.isMissingNode() || !recipes.isArray() || recipes.size() == 0)
            throw new InvalidUserPromptException("O prompt fornecido não é valido.");

        return mapper.readValue(recipes.toString(), mapper.getTypeFactory().constructCollectionType(List.class, clas));
    }

}
