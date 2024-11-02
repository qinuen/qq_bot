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

//通过第三方接口调用openai图片模型
public class ChatGptImgApi {
    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    private static final String openAiKey = "这里填入你的key";

    private static final String URL = "这里填入请求地址";

    public ChatGptImgApi() {
        this.httpClient = HttpClients.createDefault();
        this.objectMapper = new ObjectMapper();
    }

    public String autoReplay(String prompt) throws IOException {
        HttpPost httpPost = new HttpPost(URL);
        httpPost.setHeader("Authorization", "Bearer " + openAiKey);
        httpPost.setHeader("Content-Type", "application/json");
        // 构造请求体
        String requestBody = String.format("{\"model\": \"dall-e-3\", \"prompt\": \"%s\", \"n\": 1, \"quality\": \"hd\", \"size\": \"1792x1024\"}", prompt);
        httpPost.setEntity(new StringEntity(requestBody, StandardCharsets.UTF_8));
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            int statusCode = response.getStatusLine().getStatusCode();
            String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            if (statusCode != 200) {
                throw new RuntimeException("请求失败: " + responseString);
            }
            JsonNode rootNode = objectMapper.readTree(responseString);
            // 根据 API 的返回结构处理响应
            // 假设响应结构中包含一个 'data' 字段，其中有一个 'url' 字段指向生成的图像
            if (rootNode.has("data") && rootNode.path("data").isArray() && rootNode.path("data").size() > 0) {
                JsonNode dataNode = rootNode.path("data").get(0);
                if (dataNode.has("url")) {
                    return dataNode.path("url").asText();
                } else {
                    throw new RuntimeException("Invalid response structure: 'url' missing");
                }
            } else {
                throw new RuntimeException("Invalid response structure: 'data' missing or empty");
            }
        }
    }
}
