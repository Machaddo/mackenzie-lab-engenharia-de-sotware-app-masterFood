package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.example.demo.adapters.agents.ChefAgent;
import com.squareup.okhttp.OkHttpClient;
import org.springframework.boot.SpringBootConfiguration;

@SpringBootConfiguration
class ChefAgentTest {

    private static MockWebServer mockWebServer;

    @BeforeAll
    static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void teardown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void testGetReceipts_ReturnsResponseString() throws Exception {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody("{\"status\":\"ok\",\"receipts\":[]}")
        );

        String baseUrl = mockWebServer.url("/").toString();

        OkHttpClient client = new OkHttpClient();

        ChefAgent agent = new ChefAgent(
                "fake-token",
                baseUrl
        );

        String response = agent.get_receipts("hello");

        assertEquals("{\"status\":\"ok\",\"receipts\":[]}", response);

        var recordedRequest = mockWebServer.takeRequest();
        assertEquals("POST", recordedRequest.getMethod());
        assertEquals("/" , recordedRequest.getPath());
        assertEquals("Bearer fake-token", recordedRequest.getHeader("Authorization"));
    }

    @Test
    void testGetReceipts_ThrowsOnHTTPError() throws Exception {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(500)
        );

        String baseUrl = mockWebServer.url("/").toString();
        OkHttpClient client = new OkHttpClient();

        ChefAgent agent = new ChefAgent("token", baseUrl);

        assertThrows(Exception.class, () -> {
            agent.get_receipts("test");
        });
    }
}