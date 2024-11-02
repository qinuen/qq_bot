package com.record.record_history.controller;

import com.record.record_history.entity.RecordChat;
import com.record.record_history.service.impl.RecordChatServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qinuen
 * @since 2024-08-24
 */
@Controller
@RequestMapping("/recordChat")
public class RecordChatController {
    @Resource
    private RecordChatServiceImpl recordChatService;

    RecordChat recordChat = new RecordChat();
    @ResponseBody
    @RequestMapping("/setRecord")
    public String setRecord(@RequestParam("number")String number,@RequestParam("data")String data){
        recordChat.setChatData(data);
        recordChat.setGroupNumber(number);
        //recordChatService.save(recordChat);
        recordChatService.insertByEntity(number,data,"313854264");
        System.out.println("远程方法被调用");
        return "调用成功";
    }

}
