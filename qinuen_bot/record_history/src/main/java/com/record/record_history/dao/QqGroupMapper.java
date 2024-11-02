package com.record.record_history.dao;

import com.record.record_history.entity.QqGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qinuen
 * @since 2024-08-24
 */
@Mapper
public interface QqGroupMapper extends BaseMapper<QqGroup> {

    boolean setUserCity(String id,String city);

}
