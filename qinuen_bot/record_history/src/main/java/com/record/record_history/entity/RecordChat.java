package com.record.record_history.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author qinuen
 * @since 2024-08-24
 */
@Getter
@Setter
@TableName("record_chat")
public class RecordChat implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "chat_id", type = IdType.AUTO)
    private Integer chatId;

    private String groupNumber;

    private String chatData;

    private String sendUser;
}
