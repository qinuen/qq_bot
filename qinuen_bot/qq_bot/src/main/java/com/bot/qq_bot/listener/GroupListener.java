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

@Component
public class GroupListener {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Resource
    private ObjectMapper objectMapper;

    ChatGptTextApi chatGptTextApiApi = new ChatGptTextApi();

    @Autowired
    private RecordFeignService recordFeignService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private GroupMessageApi groupMessageApi;

    @Autowired
    private RandomUtil randomUtil;

    Map<String,String>userFlag;

    //绑定城市
    @Listener(priority = 200)
    @ContentTrim //去除前后空格
    @Filter(matchType = MatchType.TEXT_STARTS_WITH,value = "绑定城市",targets = @Filter.Targets(atBot = true))
    public void setCity(GroupMessageEvent groupMessageEvent) throws IOException, URISyntaxException {
        if(redisUtil.get(groupMessageEvent.getAuthor().getId().toString()).equals("0")){
            return;
        }
        redisUtil.set(groupMessageEvent.getAuthor().getId().toString(),"0");
        String msg = groupMessageEvent.getMessageContent().getPlainText();
        String city = msg.substring(msg.lastIndexOf("=")+1);
        try {
            recordFeignService.setUserCity(groupMessageEvent.getAuthor().getId().toString(),groupMessageEvent.getGroup().getId().toString(),city);
            groupMessageEvent.replyBlocking("绑定成功");
        }catch (Exception e){
            groupMessageEvent.replyBlocking("绑定失败，请检查输入格式是否为@bot 绑定城市=你所在的城市");
        }
    }

    /**
     * 今日随机老婆（暂时存在多人同一位的问题）
     * @param groupMessageEvent
     */
    @Listener(priority = 200)
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
        //获取群成员数量（除bot和消息发送者）
        int groupUserNumber = groupMemberItems.collectToList().size()-1;
        //遍历群成员列表
        List<String> allGroupUser = new ArrayList<>();
        for (GroupMember groupMember : groupMemberItems.collectToList()){
            //groupMessageEvent.replyBlocking("转list前的群成员有"+groupMember.getNickname()+" qq为："+groupMember.getId());
            allGroupUser.add(groupMember.getId().toString());
        }
        //判断今日是否已生成过
        if(redisUtil.hasKey("wife:"+qq+"-"+groupMessageEvent.getGroup().getId())){
            String wife = (String) redisUtil.get("wife:"+qq);
            messagesBuilder = new MessagesBuilder().text("你今日的老婆是：").append().at(Identifies.ID(wife)).build();
            groupMessageEvent.replyBlocking(messagesBuilder);
        }else {
            //遍历list，删除用户本人，防止自己选中自己
            for(int i=0;i<allGroupUser.size();i++){
                if(groupMessageEvent.getAuthor().getId().toString().equals(allGroupUser.get(i))){
                    allGroupUser.remove(i);
                    break;
                }
            }
            //生成今日老婆
            String todayWife = allGroupUser.get(randomUtil.randomOneNumber(0,groupUserNumber)-1);
            //生成结果放入Redis
            redisUtil.set("wife:"+qq+"-"+groupMessageEvent.getGroup().getId(),todayWife);
            // 获取当前时间
            LocalDateTime now = LocalDateTime.now();
            // 获取当天的第二个24小时周期的开始时间
            LocalDateTime secondDayStart = now.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
            // 计算差值
            Duration durationUntilSecondDayStart = Duration.between(now, secondDayStart);
            //根据时间差值设置第二天零点过期
            long secondsUntilSecondDayStart = durationUntilSecondDayStart.getSeconds();
            redisUtil.expire("wife:"+qq+"-"+groupMessageEvent.getGroup().getId(),secondsUntilSecondDayStart);
            //生成今日老婆
            messagesBuilder = new MessagesBuilder().text("你今日的老婆是：").append().at(Identifies.ID(todayWife)).build();
            groupMessageEvent.replyBlocking(messagesBuilder);
        }
    }

    //定时
    @Listener(priority = 200)
    @ContentTrim //去除前后空格
    @Filter(matchType = MatchType.TEXT_STARTS_WITH,value = "定时",targets = @Filter.Targets(atBot = true))
    public void ftbnsGroupTiming(GroupMessageEvent groupMessageEvent) throws IOException {
        if(redisUtil.get(groupMessageEvent.getAuthor().getId().toString()).equals("0")){
            return;
        }
        redisUtil.set(groupMessageEvent.getAuthor().getId().toString(),"0");
        String groupMessage = groupMessageEvent.getMessageContent().getPlainText();
        String jsonResponse = chatGptTextApiApi.alarmClockReplay(groupMessage);
        // 将 JSON 字符串解析为 Map
        Map<String, Object> responseMap = objectMapper.readValue(jsonResponse, HashMap.class);
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

    //AI自动消息回复
    @Listener(priority = 900)
    @Filter(targets = @Filter.Targets(atBot = true))
    public void ftbnsGroupAutoMessage(GroupMessageEvent groupMessageEvent) throws IOException {
        if(redisUtil.get(groupMessageEvent.getAuthor().getId().toString()).equals("0")){
            return;
        }
        redisUtil.set(groupMessageEvent.getAuthor().getId().toString(),"0");
        String groupMessage = groupMessageEvent.getMessageContent().getPlainText();
        Message message = new MessagesBuilder().text(" "+chatGptTextApiApi.autoReplay(groupMessage)).build();
        groupMessageEvent.replyBlocking(message);
    }
}
