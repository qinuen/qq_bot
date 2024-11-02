package com.bot.qq_bot.entity;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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
public class RecordChat implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer chatId;

    private String groupNumber;

    private String chatData;

    private String sendUser;
}
