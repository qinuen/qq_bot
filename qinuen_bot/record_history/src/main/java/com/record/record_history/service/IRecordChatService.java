package com.record.record_history.service;

import com.record.record_history.entity.RecordChat;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qinuen
 * @since 2024-08-24
 */
public interface IRecordChatService extends IService<RecordChat> {

    boolean insertByEntity(String group_number,String chat_data,String send_user);


}
