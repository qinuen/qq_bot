package com.bot.qq_bot.listener;

import com.bot.qq_bot.api.GroupMessageApi;
import com.bot.qq_bot.api.chatgpt.ChatGptTextApi;
import com.bot.qq_bot.feign.RecordFeignService;
import com.bot.qq_bot.util.RandomUtil;
import com.bot.qq_bot.util.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import love.forte.simboot.annotation.ContentTrim;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Listener;
import love.forte.simboot.filter.MatchType;
import love.forte.simbot.ID;
import love.forte.simbot.Identifies;
import love.forte.simbot.bot.Bot;
import love.forte.simbot.bot.OriginBotManager;
import love.forte.simbot.definition.Group;
import love.forte.simbot.definition.GroupMember;
import love.forte.simbot.event.FriendMessageEvent;
import love.forte.simbot.event.GroupMessageEvent;
import love.forte.simbot.message.Message;
import love.forte.simbot.message.MessagesBuilder;
import love.forte.simbot.utils.item.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 公共业务的中央控制器
 */
@Component
public class SysListener {

    @Autowired
    private RedisUtil redisUtil;

    //存储用户回复flag
    @Listener(priority = 1)
    public void setUserFlag(GroupMessageEvent groupMessageEvent){
        String senderQQ = groupMessageEvent.getAuthor().getId().toString();
        //为1表示待回复，为0则为已回复
        redisUtil.set(senderQQ,"1");
    }

    //私信免责声明
    @Listener
    public void privateAutoMessage(FriendMessageEvent friendMessageEvent){
        friendMessageEvent.getFriend().sendBlocking("当前bot仍在开发调试阶段，部分回复基于第三方api，结果仅供参考，不代表开发者本人立场。如有提前问题可联系开发者：313854264@qq.com");
    }

    //群聊帮助指令
    @Listener(priority = 200)
    @ContentTrim //去除前后空格
    @Filter(value = "help",targets = @Filter.Targets(atBot = true))
    public void groupHelpOrder(GroupMessageEvent groupMessageEvent){
        if(redisUtil.get(groupMessageEvent.getAuthor().getId().toString()).equals("0")){
            return;
        }
        redisUtil.set(groupMessageEvent.getAuthor().getId().toString(),"0");
        groupMessageEvent.getGroup().sendBlocking("1.@bot+消息为自动回复"+"\n"+"2.@bot 定时 你之后要做的事情bot会在时间到后回复你"+"\n"+"3.发送为@bot 绑定城市=地名 即可绑定城市或县区（地名不需要加行政区划分单位），特殊天气将会发出消息提醒，每个账号只能绑定一个聊天群和城市，可精确到区县，重复此命令会更新信息"+"\n"+"4.@bot 今日老婆 随机指定一位群友为今日老婆，其他功能仍在开发中");
    }

    //标志重置
    @Listener(priority = 1000)
    public void resetFlag(GroupMessageEvent groupMessageEvent){
        if(redisUtil.get(groupMessageEvent.getAuthor().getId().toString()).equals("0")){
            return;
        }
        redisUtil.del(groupMessageEvent.getAuthor().getId().toString());
    }

}
