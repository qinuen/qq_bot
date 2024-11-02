package com.record.record_history.service;

import com.record.record_history.entity.WeatherGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qinuen
 * @since 2024-09-06
 */
public interface IWeatherGroupService extends IService<WeatherGroup> {
    boolean serUserCity(String qq,String group,String city);
    //所有待查询的城市
    Set<String> getCityList();
    List<Map<String,String>> selectAllMap();

}
