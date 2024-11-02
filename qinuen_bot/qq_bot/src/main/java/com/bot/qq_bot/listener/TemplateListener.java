package com.bot.qq_bot.listener;


import com.bot.qq_bot.api.chatgpt.ChatGptImgApi;
import com.bot.qq_bot.api.chatgpt.ChatGptTextApi;
import com.bot.qq_bot.feign.RecordFeignService;
import com.bot.qq_bot.util.RandomUtil;
import com.bot.qq_bot.util.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import love.forte.simboot.annotation.ContentTrim;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Listener;
import love.forte.simboot.filter.MatchType;
import love.forte.simbot.ID;
import love.forte.simbot.Identifies;
import love.forte.simbot.action.MuteSupport;
import love.forte.simbot.application.BotManagers;
import love.forte.simbot.bot.Bot;
import love.forte.simbot.bot.BotManager;
import love.forte.simbot.bot.OriginBotManager;
import love.forte.simbot.component.mirai.bot.MiraiBot;
import love.forte.simbot.definition.Group;
import love.forte.simbot.definition.GroupMember;
import love.forte.simbot.event.FriendMessageEvent;
import love.forte.simbot.event.GroupMessageEvent;
import love.forte.simbot.message.Message;
import love.forte.simbot.message.Messages;
import love.forte.simbot.message.MessagesBuilder;
import love.forte.simbot.resources.PathResource;
import love.forte.simbot.resources.Resource;
import love.forte.simbot.utils.item.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class TemplateListener {

    ChatGptTextApi chatGptApi = new ChatGptTextApi();

    ChatGptImgApi chatGptImgApi = new ChatGptImgApi();
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RandomUtil randomUtil;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    Map<String,Boolean>userFlag = new HashMap<>();

    @Autowired
    private RecordFeignService recordFeignService;

    //测试
    @Listener(priority = 10)
    @ContentTrim //去除前后空格
    @Filter(value = "你好啊",targets = @Filter.Targets(authors = {"313854264"},atBot = true,groups = {"945993688"}))
    public void testPrivateMessage(GroupMessageEvent groupMessageEvent){
        if(redisUtil.get(groupMessageEvent.getAuthor().getId().toString()).equals("0")){
            return;
        }
        redisUtil.set(groupMessageEvent.getAuthor().getId().toString(),"0");
        System.out.println("收到的群聊消息:"+groupMessageEvent.getMessageContent().getPlainText());
        groupMessageEvent.getGroup().sendBlocking("群聊回复：你也好啊");
    }

    // 定时
    @Listener
    @ContentTrim // 去除前后空格
    @Filter(matchType = MatchType.TEXT_STARTS_WITH, value = "定时", targets = @Filter.Targets(atBot = true, groups = {"945993688"}))
    public void ftbnsGroupTiming(GroupMessageEvent groupMessageEvent) throws IOException {
        if(redisUtil.get(groupMessageEvent.getAuthor().getId().toString()).equals("0")){
            return;
        }
        redisUtil.set(groupMessageEvent.getAuthor().getId().toString(),"0");
        log.info("开始执行定时方法");
        String groupMessage = groupMessageEvent.getMessageContent().getPlainText();
        String jsonResponse = chatGptApi.alarmClockReplay(groupMessage);
        // 将 JSON 字符串解析为 Map
        Map<String, Object> responseMap = objectMapper.readValue(jsonResponse, HashMap.class);
        log.info("Response Map: {}", responseMap);
        // 处理 time 字段
        Object timeObject = responseMap.get("time");
        long sleepTime;
        if (timeObject instanceof Integer) {
            sleepTime = ((Integer) timeObject).longValue();
        } else if (timeObject instanceof String) {
            sleepTime = Long.parseLong((String) timeObject);
        } else {
            throw new IllegalArgumentException("time解析失败: " + timeObject.getClass().getName());
        }
        String wakeupMessage = (String) responseMap.get("message");
        // 使用 ScheduledExecutorService 来实现定时任务
        scheduler.schedule(() -> {
            try {
                groupMessageEvent.replyBlocking(wakeupMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, sleepTime, TimeUnit.MILLISECONDS);
    }

    //画图
    @Listener
    @ContentTrim //去除前后空格
    @Filter(matchType = MatchType.TEXT_STARTS_WITH,value = "画图",targets = @Filter.Targets(atBot = true,groups = {"945993688"}))
    public void ftbnsGroupImg(GroupMessageEvent groupMessageEvent) throws IOException {
        if(redisUtil.get(groupMessageEvent.getAuthor().getId().toString()).equals("0")){
            return;
        }
        redisUtil.set(groupMessageEvent.getAuthor().getId().toString(),"0");
        String groupMessage = groupMessageEvent.getMessageContent().getPlainText();
        log.info("开始作图，提示词为："+groupMessage);
        String imgUrl = chatGptImgApi.autoReplay(groupMessage);
        groupMessageEvent.replyBlocking("图片生成完毕，请点击链接查看（加载速度可能较慢，请耐心等待）"+imgUrl);
    }

    //发送图片
    @Listener
    @ContentTrim //去除前后空格
    @Filter(matchType = MatchType.TEXT_STARTS_WITH,value = "发送图片",targets = @Filter.Targets(atBot = true,groups = {"945993688"}))
    public void ftbnsGroupPic(GroupMessageEvent groupMessageEvent) throws IOException, URISyntaxException {
        if(redisUtil.get(groupMessageEvent.getAuthor().getId().toString()).equals("0")){
            return;
        }
        redisUtil.set(groupMessageEvent.getAuthor().getId().toString(),"0");
        String groupMessage = groupMessageEvent.getMessageContent().getPlainText();
        log.info("进入发送图片方法");
        MessagesBuilder messagesBuilder = new MessagesBuilder();
        Path path =  Paths.get(getClass().getClassLoader().getResource("img/111.jpg").toURI());
        PathResource resource = Resource.of(path);
        Messages messages = messagesBuilder.image(resource).build();
        groupMessageEvent.replyBlocking(messages);
    }

    @Listener
    @ContentTrim //去除前后空格
    @Filter(matchType = MatchType.TEXT_STARTS_WITH,value = "所有城市",targets = @Filter.Targets(atBot = true,groups = {"945993688"}))
    public void allCity(GroupMessageEvent groupMessageEvent) throws IOException, URISyntaxException {
//        if (userFlag.get(groupMessageEvent.getAuthor().getId().toString())) {
//            return;
//        }
//        userFlag.put(groupMessageEvent.getAuthor().getId().toString(), true);
//        List<String>result = recordFeignService.getCityList();
//        for(String r : result){
//            groupMessageEvent.replyBlocking(r);
//        }
    }

    //绑定城市（已被放入SysListener集中管理）
//    @Listener
//    @ContentTrim //去除前后空格
//    @Filter(matchType = MatchType.TEXT_STARTS_WITH,value = "绑定城市",targets = @Filter.Targets(atBot = true,groups = {"945993688"}))
//    public void setCity(GroupMessageEvent groupMessageEvent) throws IOException, URISyntaxException {
//        if(userFlag.get(groupMessageEvent.getAuthor().getId().toString())){
//            return;
//        }
//        userFlag.put(groupMessageEvent.getAuthor().getId().toString(),true);
//        String msg = groupMessageEvent.getMessageContent().getPlainText();
//        String city = msg.substring(msg.lastIndexOf("=")+1);
//        try {
//            recordFeignService.setUserCity(groupMessageEvent.getAuthor().getId().toString(),groupMessageEvent.getGroup().getId().toString(),city);
//            groupMessageEvent.replyBlocking("绑定成功");
//        }catch (Exception e){
//            groupMessageEvent.replyBlocking("绑定失败，请检查输入格式是否为@bot 绑定城市=你所在的城市");
//        }
//    }

    //测试@
    @Listener
    @ContentTrim //去除前后空格
    @Filter(matchType = MatchType.TEXT_STARTS_WITH,value = "测试AT",targets = @Filter.Targets(atBot = true,groups = {"945993688"}))
    public void testAt(GroupMessageEvent groupMessageEvent) throws IOException, URISyntaxException {
        if(redisUtil.get(groupMessageEvent.getAuthor().getId().toString()).equals("0")){
            return;
        }
        redisUtil.set(groupMessageEvent.getAuthor().getId().toString(),"0");
        MessagesBuilder messagesBuilder = new MessagesBuilder();
        String qq = groupMessageEvent.getAuthor().getId().toString();
        System.out.println("被@人的账号是："+qq);
        Message message = messagesBuilder.at(Identifies.ID(qq)).append().text("  测试@成功").build();
        ID botId = Identifies.ID("1207594046");
        ID groupId = Identifies.ID("945993688");
        Group group = groupMessageEvent.getGroup();
        group.sendBlocking(message);
    }

    /**
     * 今日随机老婆
     * @param groupMessageEvent
     */
    //@Listener
    @ContentTrim //去除前后空格
    @Filter(matchType = MatchType.TEXT_STARTS_WITH,value = "今日老婆",targets = @Filter.Targets(atBot = true))
    public void todayRandomWife(GroupMessageEvent groupMessageEvent){
        if(redisUtil.get(groupMessageEvent.getAuthor().getId().toString()).equals("0")){
            return;
        }
        redisUtil.set(groupMessageEvent.getAuthor().getId().toString(),"0");
        String qq = groupMessageEvent.getAuthor().getId().toString();
        Message messagesBuilder;
        ID botId = Identifies.ID("1207594046");
        Bot bot = OriginBotManager.INSTANCE.getBot(botId);
        // 获取群成员列表
        Group group = groupMessageEvent.getGroup();
        Items<GroupMember> groupMemberItems = group.getMembers();
        //获取群成员数量
        int groupUserNumber = groupMemberItems.collectToList().size();
        //遍历群成员列表
//        for (GroupMember groupMember : groupMemberItems.collectToList()){
//            groupMessageEvent.replyBlocking("群成员有"+groupMember.getNickname()+" qq为："+groupMember.getId());
//        }
        //判断今日是否已生成过
        if(redisUtil.hasKey("wife:"+qq)){
            String wife = (String) redisUtil.get("wife:"+qq);
            messagesBuilder = new MessagesBuilder().text("你今日的老婆是：").append().at(Identifies.ID(wife)).build();
            groupMessageEvent.replyBlocking(messagesBuilder);
        }else {
            //生成今日老婆
            String todayWife = groupMemberItems.collectToList().get(randomUtil.randomOneNumber(0,groupUserNumber-1)).getId().toString();
            //生成结果放入Redis
            redisUtil.set("wife:"+qq,todayWife);
            //生成今日老婆
            messagesBuilder = new MessagesBuilder().text("你今日的老婆是：").append().at(Identifies.ID(todayWife)).build();
            groupMessageEvent.replyBlocking(messagesBuilder);
        }
    }


    //回复消息
    @Listener(priority = 999)
    @ContentTrim //去除前后空格
    @Filter(targets = @Filter.Targets(authors = {"313854264"},atBot = true,groups = {"945993688"}))
    public void testBack(GroupMessageEvent groupMessageEvent) throws IOException {
        if(redisUtil.get(groupMessageEvent.getAuthor().getId().toString()).equals("0")){
            return;
        }
        redisUtil.set(groupMessageEvent.getAuthor().getId().toString(),"0");
        log.info("开始执行自动回复方法");
        String groupMessage = groupMessageEvent.getMessageContent().getPlainText();
        Message message = new MessagesBuilder().text(chatGptApi.autoReplay(groupMessage)).build();
        //Message message = new MessagesBuilder().text("群聊消息回复：你好啊").build();
        System.out.println("收到的群聊消息:"+groupMessageEvent.getMessageContent().getPlainText());
//        RecordChat recordChat = new RecordChat();
//        recordChat.setGroupNumber("123456");
//        recordChat.setChatData(groupMessage);
        //String results = recordFeignService.setRecord("123456",groupMessage);
        //dSystem.out.println("收到的返回结果："+results);
//        Group group = groupMessageEvent.getGroup();//获取群
//        ID id = group.getId();//获取群号
//        GroupMember grou =groupMessageEvent.getGroup().getMember(id);//获取群成员
        groupMessageEvent.replyBlocking(message);
    }

}

