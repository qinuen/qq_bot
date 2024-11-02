# QQ Bot Project (基于 Simbot 3)

## 项目简介
此项目是一个基于 [Simbot 3](https://github.com/simple-robot/simpler-robot/tree/v3-main) 开发的 QQ 机器人。因为服务器配置原因使用了spring cloud来调用另一台运行了mysql的服务器的数据并开发，结合 Redis、MySQL，提供了一些 QQ 群聊互动功能（如接入了chatGPT,天气预警）。项目现已迁移至 Simbot 4，因此此版本将作为开源示例保留。


## 配置说明
根据你的需求，项目中使用了 Spring Cloud、Redis 和 MySQL，你可以根据实际环境填写配置文件中的留白部分。默认配置文件 `application.yml` 中已包含基本的配置模板：

