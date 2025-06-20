package top.molab.minecraft.mlCommand.PluginMessage.handlers;

import com.google.gson.internal.LinkedTreeMap;
import com.velocitypowered.api.proxy.messages.ChannelMessageSink;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import moe.caa.multilogin.api.MultiLoginAPIProvider;
import top.molab.minecraft.MLCommand.Core.DTO.PlayerLoginData;
import top.molab.minecraft.MLCommand.Core.DTO.PlayerServiceData;
import top.molab.minecraft.MLCommand.Core.pluginMessage.MessageTypes;
import top.molab.minecraft.MLCommand.Core.pluginMessage.PluginMessage;
import top.molab.minecraft.MLCommand.Core.utils.ClassCastUtil;
import top.molab.minecraft.MLCommand.Core.utils.StringUtils;
import top.molab.minecraft.mlCommand.ConfigManager;
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

    PlayerServiceData playerServiceData = ClassCastUtil.getPlayerServiceFromLinkedTreeMap((LinkedTreeMap) _data.getData());
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
    loginData.setFirstLogin(false);
    // 构造full of shit的问那一车服务器这个玩家是不是首次登陆的请求
    MLCommandVelocity.getInstance().getLogger().info("get player data request: {}", loginData.getName());

    PluginMessage message = new PluginMessage();
    message.setData(loginData);
    message.setEcho(StringUtils.getRandomEchoString());
    message.setMessageType(MessageTypes.GET_PLAYER_LOGIN_INFO);

    // 获取服务器名字
    String serverName = "";
    for (RegisteredServer s: MLCommandVelocity.getInstance().getProxyServer().getAllServers()){
      if (s.getPlayersConnected().stream().toList().contains(MLCommandVelocity.getInstance().getProxyServer().getPlayer(loginData.getUuid()).orElseThrow())){
        serverName = s.getServerInfo().getName();
      };
    }

    // 创建并添加任务
    PluginMessageListener.getInstance().addTask(new Task(
            ConfigManager.getInstance().getConfig().getServerCount(),
            message.getEcho(),
            serverName,
            playerServiceData

    ));

    // 请求其他服务器发送玩家是不是首次登录，应该在隔壁GetLoginDataReplyHandler中处理
    // 回调地狱开启，救我，求你！！！！！！
    for (ChannelMessageSink s: MLCommandVelocity.getInstance().getProxyServer().getAllServers()){
      s.sendPluginMessage(PluginMessageListener.getInstance().IDENTIFIER, message.toBytes());
    }
  }
}
