package com.bot.qq_bot.feign;

import com.bot.qq_bot.entity.RecordChat;
import com.bot.qq_bot.entity.WeatherGroup;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@FeignClient(name = "record-history")
public interface RecordFeignService {
    //要调用的服务名称
    @RequestMapping("/recordChat/setRecord")
    String setRecord(@RequestParam("number")String number,@RequestParam("data")String data);
    //绑定城市
    @PostMapping("/weatherGroup/setUserCity")
    boolean setUserCity(@RequestParam("qq")String qq,@RequestParam("group")String group,@RequestParam("city")String city);
    //所有需要查询天气的城市
    @GetMapping("/weatherGroup/getCityList")
    Set<String> getCityList();
    //需要被通知到的用户
    @GetMapping("/weatherGroup/getAllWeatherUser")
    List<WeatherGroup>getAllWeatherUser();

}
