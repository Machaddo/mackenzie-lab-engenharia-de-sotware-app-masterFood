package com.example.demo.adapters.filters;

import com.example.demo.Utils.JsonSchema;
import java.util.Map;

public class RecipeFilter implements Filter {

    private final String filter = "Determine se o texto abaixo em letras maiúsculas é uma solicitação por uma receita culinária\n"
            + "%s\n"
            + "Responda apenas com JSON válido no formato: {\"is_filtered\": boolean}";

    private String userPrompt;
    private Map<String, Object> schema;

    public RecipeFilter(String userPrompt) {
        this.schema = JsonSchema.getFilterSchema();
        this.userPrompt = userPrompt;
    }

    @Override
    public Map<String, Object> getSchema() {
        return schema;
    }

    @Override
    public String getFilter() {
        return String.format(filter, userPrompt.toUpperCase());
    }

}
