package com.example.wowshop.chat;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class GeminiChatService {

    private final RestClient restClient;
    private final String apiKey;
    private final String model;

    public GeminiChatService(
            RestClient.Builder restClientBuilder,
            @Value("${gemini.api-key:}") String apiKey,
            @Value("${gemini.model:gemini-2.5-flash}") String model
    ) {
        this.restClient = restClientBuilder
                .baseUrl("https://generativelanguage.googleapis.com")
                .build();
        this.apiKey = apiKey;
        this.model = model;
    }

    public String chat(String message) {
        if (apiKey == null || apiKey.isBlank()) {
            return "챗봇 API 키가 아직 설정되지 않았습니다. 서버 실행 전 GEMINI_API_KEY 환경변수를 설정해 주세요.";
        }

        Map<String, Object> request = Map.of(
                "systemInstruction", Map.of(
                        "parts", List.of(Map.of(
                                "text",
                                "너는 WOW MARKET 쇼핑몰의 친절한 한국어 쇼핑 상담원이다. " +
                                        "상품 추천, 회원가입, 로그인, 장바구니, 주문 방법을 짧고 실용적으로 안내한다. " +
                                        "모르는 내용은 추측하지 말고 쇼핑몰에서 확인하라고 말한다."
                        ))
                ),
                "contents", List.of(Map.of(
                        "role", "user",
                        "parts", List.of(Map.of("text", message))
                )),
                "generationConfig", Map.of(
                        "temperature", 0.6,
                        "maxOutputTokens", 350
                )
        );

        JsonNode response = restClient.post()
                .uri("/v1beta/models/{model}:generateContent", model)
                .header("x-goog-api-key", apiKey)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(request)
                .retrieve()
                .body(JsonNode.class);

        JsonNode parts = response == null
                ? null
                : response.path("candidates").path(0).path("content").path("parts");
        if (parts == null || !parts.isArray() || parts.isEmpty()) {
            return "답변을 생성하지 못했습니다. 잠시 후 다시 시도해 주세요.";
        }

        StringBuilder reply = new StringBuilder();
        for (JsonNode part : parts) {
            String text = part.path("text").asText("");
            if (!text.isBlank()) {
                reply.append(text).append("\n");
            }
        }
        return reply.toString().trim();
    }
}
