package com.bot.qq_bot.listener;

import com.bot.qq_bot.api.chatgpt.ChatGptImgApi;
import com.bot.qq_bot.api.chatgpt.ChatGptTextApi;
import com.bot.qq_bot.feign.RecordFeignService;
import com.bot.qq_bot.util.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import love.forte.simboot.annotation.ContentTrim;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Listener;
import love.forte.simboot.filter.MatchType;
import love.forte.simbot.event.FriendMessageEvent;
import love.forte.simbot.event.GroupMessageEvent;
import love.forte.simbot.message.Message;
import love.forte.simbot.message.MessagesBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
//废避难所3.0群
public class FtbnsGroupListener {
    ChatGptTextApi chatGptTextApiApi = new ChatGptTextApi();

    ChatGptImgApi chatGptImgApi = new ChatGptImgApi();
    @Resource
    private ObjectMapper objectMapper;

    @Autowired
    private RedisUtil redisUtil;


    //画图
    @Listener
    @ContentTrim //去除前后空格
    @Filter(matchType = MatchType.TEXT_STARTS_WITH,value = "画图",targets = @Filter.Targets(atBot = true,groups = {"632246401"}))
    public void ftbnsGroupImg(GroupMessageEvent groupMessageEvent) throws IOException {
        if(redisUtil.get(groupMessageEvent.getAuthor().getId().toString()).equals("0")){
            return;
        }
        redisUtil.set(groupMessageEvent.getAuthor().getId().toString(),"0");
        String groupMessage = groupMessageEvent.getMessageContent().getPlainText();
        String imgUrl = chatGptImgApi.autoReplay(groupMessage);
        groupMessageEvent.replyBlocking("图片生成完毕，请点击链接查看（加载速度可能较慢，请耐心等待）,图片生成一次的成本将近两块钱，别把我余额干爆了"+imgUrl);
    }




}
