# MultiLoginCommand

![MoPlugins](https://img.shields.io/badge/PoweredBy-OpenMoPlugin-blue)

![License](https://img.shields.io/github/license/moran0710/MultiLoginCommand)

在玩家使用猫踢螺钉登录时执行命令！

## 插件介绍

MultiLoginCommand时一个MultiLogin拓展，在玩家使用MultiLogin登录子服时，可以在子服执行对应命令

文档地址：[OpenMoPlugins](https://openmo.molab.top/bukkit/moChainMining/)

 - 支持设置首次登陆执行的命令
 - 支持对使用不同的登录凭证的玩家，执行不同的命令组

## 安装

你需要在Velocity端安装MultiLogin作为前置，在Bukkit端安装PlaceholderAPI作为前置

1. 去release下载MultiLoginCommand，分为Velocity版本和Bukkit版本
2. 将Velocity版安装在Velocity，Bukkit版安装在Bukkit端
3. 配置Velocity端，只需要设置`serverCount`参数，填入你安装了Bukkit版的子服个数
4. 配置Bukkit端，在groups组配置下按照例子填写新的组，具体请查阅文档

## 指令列表

Bukkit端只有一个/mlcommand命令，重载配置文件，需要`mlcommand.reload`权限

Velocity端无命令

## 其他

如果你有建议/Bug反馈，可以在[Github Issue](https://github.com/moran0710/MultiLoginCommand/issues)反馈

反馈前请先查看是否有人提出了相同问题
