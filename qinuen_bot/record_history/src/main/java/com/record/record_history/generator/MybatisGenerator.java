package com.record.record_history.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.nio.file.Paths;

public class MybatisGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/qq_bot?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&useSSL=false", "root", "sxmesm")
                .globalConfig(builder -> builder
                        .author("qinuen")
                        .outputDir(Paths.get(System.getProperty("user.dir")) + "/record_history/src/main/java")
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig(builder -> builder
                        .parent("com.record.record_history")
                        .entity("entity")
                        .mapper("dao")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("dao.xml")
                )
                .strategyConfig(builder -> builder
                        .entityBuilder().enableLombok()
                        .mapperBuilder().enableMapperAnnotation()//添加@mapper注解
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
