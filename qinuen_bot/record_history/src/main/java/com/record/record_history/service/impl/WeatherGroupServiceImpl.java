package com.record.record_history.service.impl;

import com.record.record_history.entity.WeatherGroup;
import com.record.record_history.dao.WeatherGroupMapper;
import com.record.record_history.service.IWeatherGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.record.record_history.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qinuen
 * @since 2024-09-06
 */
@Service
public class WeatherGroupServiceImpl extends ServiceImpl<WeatherGroupMapper, WeatherGroup> implements IWeatherGroupService {

    @Autowired
    private WeatherGroupMapper weatherGroupMapper;

    @Override
    public boolean serUserCity(String qq,String group, String city) {
        String date = DateUtil.getCurrentDateTimeString();
        return weatherGroupMapper.serUserCity(qq,group,city,date);
    }

    @Override
    public Set<String> getCityList() {
        return weatherGroupMapper.getCityList();
    }

    @Override
    public List<Map<String,String>> selectAllMap() {
        return weatherGroupMapper.selectAllMap();
    }


}
