package com.record.record_history.dao;

import com.record.record_history.entity.WeatherGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qinuen
 * @since 2024-09-06
 */
@Mapper
public interface WeatherGroupMapper extends BaseMapper<WeatherGroup> {
    //绑定用户所在城市
    boolean serUserCity(@Param("qq") String qq,@Param("group") String group, @Param("city") String city, @Param("date")String date);

    List<Map<String,String>>selectAllMap();
    //所有待查询的城市
    Set<String> getCityList();
}
