package com.example.demo.adapters.agents;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.Utils.JsonSchema;
import com.example.demo.model.exceptions.InvalidApiResponseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

@Service
public class ChefAgent implements Agent {

    @Value("API.TOKEN")
    private final String TOKEN;

    @Value("API.URL")
    private final String URL;

    private final OkHttpClient client;

    public ChefAgent(String token, String url) {
        TOKEN = token;
        URL = url;
        client = new OkHttpClient();
    }

    @Override
    public String get_receipts(String prompt) throws Exception {
        Map<String, Object> body = Map.of(
                "model", "openai/gpt-4.1-nano",
                "response_format", Map.of(
                        "type", "json_schema",
                        "json_schema", JsonSchema.getRecipetSchema()),
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", prompt)));

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(body);

        Request request = new Request.Builder()
            .url(URL)
            .addHeader("Authorization", "Bearer " + TOKEN)
            .addHeader("Content-Type", "application/json")
            .post(RequestBody.create(MediaType.parse("application/json"), requestBody))
            .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()){
            throw new InvalidApiResponseException("Não foi possivel gerar uma resposta valida;");
        }

        return response.body().string();
    }

}