package com.bot.qq_bot.api;

import love.forte.simbot.ID;
import love.forte.simbot.Identifies;
import love.forte.simbot.bot.Bot;
import love.forte.simbot.bot.OriginBotManager;
import love.forte.simbot.definition.Group;
import love.forte.simbot.message.Message;
import love.forte.simbot.message.MessagesBuilder;
import org.springframework.stereotype.Component;

/**
 * 群聊相关
 */
@Component
public class GroupMessageApi {
    //主动推送消息
    public void sendGroupMessage(String group_number,String user_number,String message){
        Message messagesBuilder;
        if(user_number == null || user_number.equals("")){
            messagesBuilder = new MessagesBuilder().text(message).build();

        }else {
            messagesBuilder = new MessagesBuilder().at(Identifies.ID(user_number)).append().text(message).build();
        }
        ID botId = Identifies.ID("1207594046");
        ID groupId = Identifies.ID(group_number);
        Bot bot = OriginBotManager.INSTANCE.getBot(botId);
        Group group = bot.getGroup(groupId);
        group.sendBlocking(messagesBuilder);
    }
}
