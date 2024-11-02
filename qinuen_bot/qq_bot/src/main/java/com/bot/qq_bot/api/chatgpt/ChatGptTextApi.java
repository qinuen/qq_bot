package com.bot.qq_bot.api.chatgpt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.nio.charset.StandardCharsets;

//通过第三方接口调用openai文字模型

public class ChatGptTextApi {
    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    private static final String openAiKey = "这里输入你的key";

    private static final String URL = "这里输入请求地址";

    public ChatGptTextApi() {
        this.httpClient = HttpClients.createDefault();
        this.objectMapper = new ObjectMapper();
    }

    public String autoReplay(String prompt) throws IOException {
        HttpPost httpPost = new HttpPost(URL);
        httpPost.setHeader("Authorization", "Bearer " + openAiKey);
        httpPost.setHeader("Content-Type", "application/json");
        String requestBody = String.format("{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}",prompt);
        httpPost.setEntity(new StringEntity(requestBody, StandardCharsets.UTF_8));
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            int statusCode = response.getStatusLine().getStatusCode();
            String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            if (statusCode != 200) {
                throw new RuntimeException("请求失败: " + responseString);
            }
            JsonNode rootNode = objectMapper.readTree(responseString);
            if (rootNode.has("choices") && rootNode.path("choices").isArray() && rootNode.path("choices").size() > 0) {
                JsonNode choicesNode = rootNode.path("choices").get(0);
                if (choicesNode.has("message") && choicesNode.path("message").has("content")) {
                    return choicesNode.path("message").path("content").asText();
                } else {
                    throw new RuntimeException("Invalid response structure: 'message' or 'content' missing");
                }
            } else {
                throw new RuntimeException("Invalid response structure: 'choices' missing or empty");
            }
        }
    }

    public String alarmClockReplay(String prompt) throws IOException {
        String text = "解析以下内容，返回给我json格式的数据（time和message），time是用户想要时间距离现在的毫秒数，message是闹钟到时后你给他的消息(是闹钟到时后的消息不是你收到定时要求时的消息并且用中文回复：)";
        HttpPost httpPost = new HttpPost(URL);
        httpPost.setHeader("Authorization", "Bearer " + openAiKey);
        httpPost.setHeader("Content-Type", "application/json");
        String requestBody = String.format("{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}",text+prompt);
        httpPost.setEntity(new StringEntity(requestBody, StandardCharsets.UTF_8));
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            int statusCode = response.getStatusLine().getStatusCode();
            String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            if (statusCode != 200) {
                throw new RuntimeException("请求失败: " + responseString);
            }
            JsonNode rootNode = objectMapper.readTree(responseString);
            if (rootNode.has("choices") && rootNode.path("choices").isArray() && rootNode.path("choices").size() > 0) {
                JsonNode choicesNode = rootNode.path("choices").get(0);
                if (choicesNode.has("message") && choicesNode.path("message").has("content")) {
                    return choicesNode.path("message").path("content").asText();
                } else {
                    throw new RuntimeException("Invalid response structure: 'message' or 'content' missing");
                }
            } else {
                throw new RuntimeException("Invalid response structure: 'choices' missing or empty");
            }
        }
    }
}