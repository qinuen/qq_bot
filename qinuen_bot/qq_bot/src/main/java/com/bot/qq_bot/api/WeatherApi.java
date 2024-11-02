package com.bot.qq_bot.api;

import com.bot.qq_bot.entity.vo.FeatureWeatherVo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**+
 * 天气类api
 * 接口来源：聚合数据
 */
@Component
public class WeatherApi {
    private static final String API_KEY = "输入你的key";

//    public static void main(String[] args) throws IOException {
//        List<FeatureWeatherVo> featureWeatherVos = getWeather("西安");
//        System.out.println(featureWeatherVos.get(1).toString());
//    }

    /**
     * 获取天气的接口
     * @param prompt 城市
     * @return
     * @throws IOException
     */
    public List<FeatureWeatherVo> getWeather(String prompt) throws IOException {
        // 创建ObjectMapper实例
        ObjectMapper objectMapper = new ObjectMapper();
        List<FeatureWeatherVo> featureWeatherVos = new ArrayList<>();
        //每日只有50次免费机会
        String API_URL = "http://apis.juhe.cn/simpleWeather/query?city=" + prompt + "&key=" + API_KEY;
        // 创建HttpClient对象
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 创建HttpGet请求
            HttpGet httpGet = new HttpGet(API_URL);
            // 设置请求头
            httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
            // 发送请求
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                // 获取响应状态码
                int statusCode = response.getStatusLine().getStatusCode();
                // 读取响应体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // 转换响应体为字符串
                    String result = EntityUtils.toString(entity, "UTF-8");
//                    JSONObject jsonObject = JSONObject.parseObject(result);
//                    JSONObject results = jsonObject.getJSONObject("future");
                    // 解析JSON
                    JsonNode rootNode = objectMapper.readTree(result);
                    JsonNode futureNode = rootNode.path("result").path("future");
                    // 遍历future数组
                    for (JsonNode node : futureNode) {
                        FeatureWeatherVo weatherFuture = new FeatureWeatherVo();
                        weatherFuture.setDate(node.get("date").asText());
                        weatherFuture.setTemperature(node.get("temperature").asText());
                        weatherFuture.setWeather(node.get("weather").asText());
                        weatherFuture.setDirect(node.get("direct").asText());
                        featureWeatherVos.add(weatherFuture);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return featureWeatherVos;
        }
    }

}
