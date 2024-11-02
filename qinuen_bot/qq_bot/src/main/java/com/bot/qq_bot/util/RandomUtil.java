package com.bot.qq_bot.util;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 生成随机相关的工具类
 */
@Component
public class RandomUtil {

    /**
     * 生成一个指定范围内的随机数
     * @param min
     * @param max
     * @return
     */
    public int randomOneNumber(int min,int max){
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min; // 生成介于min和max之间的随机数
    }
}
