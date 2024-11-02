package com.record.record_history.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.record.record_history.entity.WeatherGroup;
import com.record.record_history.service.IWeatherGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qinuen
 * @since 2024-09-06
 */
@RestController
@RequestMapping("/weatherGroup")
public class WeatherGroupController {

    @Autowired
    private IWeatherGroupService weatherGroupService;

    /**
     * 用户所在城市绑定
     * @param qq
     * @param city
     * @return
     */
    @PostMapping("/setUserCity")
    public boolean setUserCity(String qq,String group,String city){
        QueryWrapper<WeatherGroup> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("user_qq",qq);
        WeatherGroup weatherGroup = weatherGroupService.getOne(queryWrapper);
        if(weatherGroup == null || weatherGroup.getUserQq().equals("")){
            return weatherGroupService.serUserCity(qq,group,city);
        }else {
            weatherGroup.setUserCity(city);
            return weatherGroupService.updateById(weatherGroup);
        }
    }

    /**
     * 所有待查询天气的城市
     * @return
     */
    @GetMapping("/getCityList")
    public Set<String> getCityList(){
        Set<String>results = weatherGroupService.getCityList();
        return results;
    }

    @GetMapping("/getAllWeatherUser")
    public List<WeatherGroup> getAllWeatherUser(){
//        QueryWrapper<WeatherGroup> queryWrapper =new QueryWrapper<>();
//        queryWrapper.select("user_qq","user_group");
//        List<String>results = weatherGroupService.getCityList();
        QueryWrapper<WeatherGroup> queryWrapper =new QueryWrapper<>();
        //List<Map<String,String>>results = weatherGroupService.selectAllMap();
        List<WeatherGroup>results = weatherGroupService.getBaseMapper().selectList(queryWrapper);
        return results;
    }

}
