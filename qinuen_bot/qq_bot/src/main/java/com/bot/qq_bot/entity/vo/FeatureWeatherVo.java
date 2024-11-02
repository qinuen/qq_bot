package com.bot.qq_bot.entity.vo;

import lombok.Data;

/**
 * 天气接口返回对象
 */
@Data
public class FeatureWeatherVo {
    //日期
    private String date;
    //温度
    private String temperature;
    //天气
    private String weather;
    //风向
    private String direct;


}
