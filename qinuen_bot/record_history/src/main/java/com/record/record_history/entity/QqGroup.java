package com.record.record_history.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@TableName("qq_group")
public class QqGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "group_id", type = IdType.AUTO)
    private Integer groupId;

    /**
     * 群号
     */
    private String groupNumber;

    /**
     * 群名
     */
    private String groupName;

    private LocalDateTime createDate;

    private String deleteFlag;
}
