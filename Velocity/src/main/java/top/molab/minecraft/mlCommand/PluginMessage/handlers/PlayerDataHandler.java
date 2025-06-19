package top.molab.minecraft.mlCommand.PluginMessage.handlers;

import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.messages.ChannelMessageSink;
import java.util.Objects;
import moe.caa.multilogin.api.MultiLoginAPI;
import moe.caa.multilogin.api.MultiLoginAPIProvider;
import top.molab.minecraft.MLCommand.Core.DTO.PlayerLoginData;
import top.molab.minecraft.MLCommand.Core.DTO.PlayerServiceData;
import top.molab.minecraft.MLCommand.Core.pluginMessage.MessageTypes;
import top.molab.minecraft.MLCommand.Core.pluginMessage.PluginMessage;
import top.molab.minecraft.MLCommand.Core.utils.StringUtils;
import top.molab.minecraft.mlCommand.MLCommandVelocity;
import top.molab.minecraft.mlCommand.PluginMessage.PluginMessageListener;
import top.molab.minecraft.mlCommand.Task;


/**
 * 处理Bukkit发过来的获取玩家登录状态的请求
 */
public class PlayerDataHandler implements MessageHandler {
  @Override
  public boolean canHandle(MessageTypes type) {
    return type.equals(MessageTypes.GET_PLAYER_DATA);
  }


  @Override
  public void handle(ChannelMessageSink server, PluginMessage _data) {
    // 建立玩家档案DTO
    PlayerServiceData playerServiceData = (PlayerServiceData) _data.getData();
    playerServiceData.setServiceName(
            MultiLoginAPIProvider.getApi().getPlayerData(playerServiceData.getUuid()).getLoginService().getServiceName()
    );
    playerServiceData.setServiceID(
            MultiLoginAPIProvider.getApi().getPlayerData(playerServiceData.getUuid()).getLoginService().getServiceId()
    );
    // 建立玩家登录信息DTO
    PlayerLoginData loginData = new PlayerLoginData();
    loginData.setName(playerServiceData.getName());
    loginData.setUuid(playerServiceData.getUuid());
    // 构造full of shit的问那一车服务器这个玩家是不是首次登陆的请求
    PluginMessage message = new PluginMessage();
    message.setData(loginData);
    message.setEcho(StringUtils.getRandomEchoString());
    message.setMessageType(MessageTypes.GET_PLAYER_LOGIN_INFO);

    // 创建并添加任务
    PluginMessageListener.getInstance().addTask(new Task(
            MLCommandVelocity.getInstance().getProxyServer().getAllServers().size(),
            message.getEcho(),
            server,
            playerServiceData

    ));

    // 请求其他服务器发送玩家是不是首次登录，应该在隔壁GetLoginDataReplyHandler中处理
    // 回调地狱开启，救我，求你！！！！！！
    PluginMessageListener.getInstance();
    for (ChannelMessageSink s: MLCommandVelocity.getInstance().getProxyServer().getAllServers()){
      s.sendPluginMessage(PluginMessageListener.getInstance().IDENTIFIER, message.toBytes());
    }
  }
}
