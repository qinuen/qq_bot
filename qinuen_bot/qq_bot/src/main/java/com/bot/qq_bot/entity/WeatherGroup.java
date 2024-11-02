package com.bot.qq_bot.entity;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author qinuen
 * @since 2024-09-06
 */
@Data
public class WeatherGroup implements Serializable {

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
