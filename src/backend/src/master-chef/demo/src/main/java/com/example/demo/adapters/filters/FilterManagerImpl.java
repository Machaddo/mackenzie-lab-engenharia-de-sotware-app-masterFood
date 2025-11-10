package com.example.demo.adapters.filters;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.model.exceptions.InvalidApiResponseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

@Service
public class FilterManagerImpl implements FilterManager {

    @Value("API.TOKEN")
    private String TOKEN;

    @Value("API.URL")
    private String URL;

    private List<Filter> filters;
    private OkHttpClient client;

    public FilterManagerImpl(List<Filter> filters) {
        this.filters = filters;
        this.client = new OkHttpClient();
    }

    @Override
    public boolean applyFilters() throws Exception {
        for (Filter filter : filters) {
            String requestBody = getRequestBody(filter.getSchema(), filter.getFilter());
            Request request = getRequest(requestBody);

            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                System.out.println(response.message().toUpperCase());
                throw new InvalidApiResponseException("Não foi possivel gerar uma resposta valida;");
            }

            String responseBody = response.body().string();
            boolean filterResponse = getFilterResponse(responseBody);
            if (!filterResponse)
                return false;
        }
        return true;
    }

    private String getRequestBody(Map<String, Object> schema, String filter) throws Exception {
        Map<String, Object> body = Map.of(
                "model", "openai/gpt-4.1-nano",
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", filter)));

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(body);
    }

    private Request getRequest(String requestBody) {
        return new Request.Builder()
                .url(URL)
                .addHeader("Authorization", "Bearer " + TOKEN)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(MediaType.parse("application/json"), requestBody))
                .build();
    }

    @SuppressWarnings("unchecked")
    private boolean getFilterResponse(String response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> outer = mapper.readValue(response, new TypeReference<Map<String, Object>>() {
            });
            List<Map<String, Object>> choices = (List<Map<String, Object>>) outer.get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            String content = (String) message.get("content");
            Map<String, Object> jsonContent = mapper.readValue(content, new TypeReference<Map<String, Object>>() {
            });
            Object isFiltered = jsonContent.get("is_filtered");
            return isFiltered instanceof Boolean && (Boolean) isFiltered;

        } catch (Exception e) {
            System.out.println(e.getMessage().toUpperCase());
            return false;
        }
    }

}
