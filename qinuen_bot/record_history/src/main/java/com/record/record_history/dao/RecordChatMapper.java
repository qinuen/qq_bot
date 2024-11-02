package com.record.record_history.dao;

import com.record.record_history.entity.RecordChat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qinuen
 * @since 2024-08-24
 */
@Mapper
public interface RecordChatMapper extends BaseMapper<RecordChat> {

    boolean insertByEntity(@Param("group_number") String group_number, @Param("chat_data") String chat_data, @Param("send_user") String send_user);

}
