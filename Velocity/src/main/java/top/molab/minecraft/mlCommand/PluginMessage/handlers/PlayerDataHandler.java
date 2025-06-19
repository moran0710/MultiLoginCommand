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

public class PlayerDataHandler implements MessageHandler {
  @Override
  public boolean canHandle(MessageTypes type) {
    return type.equals(MessageTypes.GET_PLAYER_DATA);
  }

  @Override
  public void handle(ChannelMessageSink server, PluginMessage _data) {
    PlayerServiceData playerServiceData = (PlayerServiceData) _data.getData();
    playerServiceData.setServiceName(
            MultiLoginAPIProvider.getApi().getPlayerData(playerServiceData.getUuid()).getLoginService().getServiceName()
    );
    playerServiceData.setServiceID(
            MultiLoginAPIProvider.getApi().getPlayerData(playerServiceData.getUuid()).getLoginService().getServiceId()
    );
    PlayerLoginData loginData = new PlayerLoginData();
    loginData.setName(playerServiceData.getName());
    loginData.setUuid(playerServiceData.getUuid());
    PluginMessage message = new PluginMessage();

    message.setData(loginData);
    message.setEcho(StringUtils.getRandomEchoString());
    message.setMessageType(MessageTypes.GET_PLAYER_LOGIN_INFO);

    PluginMessageListener.getInstance().addTask(new Task(
            MLCommandVelocity.getInstance().getProxyServer().getAllServers().size(),
            message.getEcho(),
            server,
            playerServiceData

    ));

    PluginMessageListener.getInstance();
    for (ChannelMessageSink s: MLCommandVelocity.getInstance().getProxyServer().getAllServers()){
      s.sendPluginMessage(PluginMessageListener.getInstance().IDENTIFIER, message.toBytes());
    }
  }
}
