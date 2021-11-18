# mirai-console-throw-it

[![GitHub top language](https://img.shields.io/github/languages/top/Samarium150/mirai-console-throw-it?style=flat)](https://kotlinlang.org/)
[![Gradle CI](https://github.com/Samarium150/mirai-console-throw-it/actions/workflows/Gradle%20CI.yml/badge.svg)](https://github.com/Samarium150/mirai-console-throw-it/actions/workflows/Gradle%20CI.yml)
[![GitHub](https://img.shields.io/github/license/Samarium150/mirai-console-throw-it?style=flat)](https://github.com/Samarium150/mirai-console-throw-it/blob/master/LICENSE)

Fork自 [ThrowIt-Mirai](https://github.com/MoeMegu/ThrowIt-Mirai)

重构为Kotlin

“丢人” 插件，用指令把你@的人丢出去

## Usage

将插件置于plugin目录下, 并安装chat-command插件

使用插件需要权限 com.github.samarium150.mirai-console-throw-it:command.throw

`> /perm permit m* com.github.samarium150.mirai-console-throw-it:command.throw`

聊天环境使用命令:

(/)丢 <@目标> 或 (/)throw <@目标>

使用 (/)th-clean 清理数据目录

![example](docs/example.png)

![result](docs/result.png)

## Reference

[ThrowIt-Mirai](https://github.com/MoeMegu/ThrowIt-Mirai)

[ThrowItBot](https://github.com/YJBeetle/ThrowItBot)

