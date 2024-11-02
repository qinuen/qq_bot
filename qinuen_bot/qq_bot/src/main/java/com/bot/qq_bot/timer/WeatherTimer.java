package com.bot.qq_bot.timer;

import com.bot.qq_bot.api.GroupMessageApi;
import com.bot.qq_bot.api.WeatherApi;
import com.bot.qq_bot.entity.WeatherGroup;
import com.bot.qq_bot.entity.vo.FeatureWeatherVo;
import com.bot.qq_bot.feign.RecordFeignService;
import com.bot.qq_bot.listener.SysListener;
import com.bot.qq_bot.listener.TemplateListener;
import com.bot.qq_bot.util.RedisUtil;
import com.bot.qq_bot.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * 天气相关的定时器
 */
@Component
@Slf4j
public class WeatherTimer {
    @Resource
    private WeatherApi weatherApi;

    @Resource
    private GroupMessageApi groupMessageApi;
    @Resource
    private RecordFeignService recordFeignService;

    /**
     * 每天晚上 21点30分开始发送天气预警
     */
    @Scheduled(cron = "0 30 21 * * ?")
    public void sendWeatherWaring(){
        // 使用全局线程池提交任务
        ThreadUtil.getGlobalExecutor().submit(new Runnable() {
            @Override
            public void run() {
                log.info("开始执行每日天气通知的定时任务");
                //所有需要查询天气的城市
                Set<String> allCity = recordFeignService.getCityList();
                //会有坏天气的地区
                //Set<String> badCity = new HashSet<>();
                //会下雨的地区
                Set<String> rainCity = new HashSet<>();
                //会下雪的地区
                Set<String> snowCity = new HashSet<>();
                //有雾的地区
                Set<String> fogCity = new HashSet<>();
                //极端恶劣天气（沙尘暴、暴雨、暴雪、冻雨）
                Set<String> badCity = new HashSet<>();
                //天气接口返回类
                FeatureWeatherVo featureWeatherVo = new FeatureWeatherVo();
                //查询天气接口返回类型
                Map<String, FeatureWeatherVo> weather = new HashMap<>();
                //查询天气
                for (String c : allCity) {
                    //获取次日的天气
                    try {
                        featureWeatherVo = weatherApi.getWeather(c).get(1);
                    } catch (IOException e) {
                        log.error("天气于预警出现问题");
                    }
                    if (featureWeatherVo.getWeather().contains("沙尘暴")||featureWeatherVo.getWeather().contains("暴雨")||featureWeatherVo.getWeather().contains("暴雪")||featureWeatherVo.getWeather().contains("冻雨")) {
                        rainCity.add(c);
                        weather.put(c, featureWeatherVo);
                    }
                    else if (featureWeatherVo.getWeather().contains("雨")) {
                        rainCity.add(c);
                        weather.put(c, featureWeatherVo);
                    } else if (featureWeatherVo.getWeather().contains("雪")) {
                        snowCity.add(c);
                        weather.put(c, featureWeatherVo);
                    }else if (featureWeatherVo.getWeather().contains("雾")) {
                        fogCity.add(c);
                        weather.put(c, featureWeatherVo);
                    }
                }
                //需要通知到的用户
                List<WeatherGroup> users = recordFeignService.getAllWeatherUser();
                //开始遍历通知需要通知的用户
                //极端恶劣天气通知
                for (String bc : badCity) {
                    for (int y = 0; y < users.size(); y++) {
                        if (bc.equals(users.get(y).getUserCity())) {
                            groupMessageApi.sendGroupMessage(users.get(y).getUserGroup(), users.get(y).getUserQq(), "  极端恶劣天气预警！！！"+"\n"+"明日 " + users.get(y).getUserCity() + " 的天气为：" + weather.get(users.get(y).getUserCity()).getWeather() +"\n"+ "，当日最低/最高温度为：" + weather.get(users.get(y).getUserCity()).getTemperature() + ",建议提前调整次日行程安排，注意自身安全");
                        }
                    }
                }
                //降雨通知
                for (String rn : rainCity) {
                    for (int y = 0; y < users.size(); y++) {
                        if (rn.equals(users.get(y).getUserCity())) {
                            groupMessageApi.sendGroupMessage(users.get(y).getUserGroup(), users.get(y).getUserQq(), "  明日 "+users.get(y).getUserCity()+" 的天气为："+weather.get(users.get(y).getUserCity()).getWeather()+"，当日最低/最高温度为："+weather.get(users.get(y).getUserCity()).getTemperature()+",出门记得带伞，记得提前规划出行时间和路线，降低行驶速度，小心地面湿滑");
                        }
                    }
                }
                //降雪通知
                for (String sn : snowCity) {
                    for (int y = 0; y < users.size(); y++) {
                        if (sn.equals(users.get(y).getUserCity())) {
                            groupMessageApi.sendGroupMessage(users.get(y).getUserGroup(), users.get(y).getUserQq(), "  明日 "+users.get(y).getUserCity()+" 的天气为："+weather.get(users.get(y).getUserCity()).getWeather()+"，当日最低/最高温度为："+weather.get(users.get(y).getUserCity()).getTemperature()+",记得提前规划出行时间和路线，降低行驶速度，小心地面湿滑");
                        }
                    }
                }
                //雾天通知
                for (String fog : fogCity) {
                    for (int y = 0; y < users.size(); y++) {
                        if (fog.equals(users.get(y).getUserCity())) {
                            groupMessageApi.sendGroupMessage(users.get(y).getUserGroup(), users.get(y).getUserQq(), "  明日 "+users.get(y).getUserCity()+" 的天气为："+weather.get(users.get(y).getUserCity()).getWeather()+"，当日最低/最高温度为："+weather.get(users.get(y).getUserCity()).getTemperature()+",记得提前规划出行时间和路线，降低行驶速度，穿戴醒目反光标识");
                        }
                    }
                }
            }
        });
    }

}
