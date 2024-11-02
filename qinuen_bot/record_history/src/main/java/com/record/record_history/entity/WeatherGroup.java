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
 * @since 2024-09-06
 */
@Getter
@Setter
@TableName("weather_group")
public class WeatherGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "weather_id", type = IdType.AUTO)
    private Integer weatherId;

    /**
     * 绑定的qq号
     */
    private String userQq;

    /**
     * 所在q群
     */
    private String userGroup;

    /**
     * 绑定的城市
     */
    private String userCity;

    /**
     * 创建日期
     */
    private String createDate;

    /**
     * 删除标志 NOT_DELETE
     */
    private String deleteFlag;

    // 无参构造函数
    public WeatherGroup() {}

    // 全参构造函数
    public WeatherGroup(String userQq, String userCity, String createDate, String deleteFlag) {
        this.userQq = userQq;
        this.userCity = userCity;
        this.createDate = createDate;
        this.deleteFlag = deleteFlag;
    }
}
