package top.molab.minecraft.mlCommand.PluginMessage.handlers;

import com.velocitypowered.api.proxy.messages.ChannelMessageSink;
import java.util.Objects;
import moe.caa.multilogin.api.MultiLoginAPI;
import moe.caa.multilogin.api.MultiLoginAPIProvider;
import top.molab.minecraft.MLCommand.Core.DTO.PlayerServiceData;
import top.molab.minecraft.MLCommand.Core.pluginMessage.MessageTypes;
import top.molab.minecraft.MLCommand.Core.pluginMessage.PluginMessage;
import top.molab.minecraft.mlCommand.PluginMessage.PluginMessageListener;

public class PlayerDataHandler implements MessageHandler {
  @Override
  public boolean canHandle(MessageTypes type) {
    return type.equals(MessageTypes.GET_PLAYER_DATA);
  }

  @Override
  public void handle(ChannelMessageSink server, Object data) {
    MultiLoginAPI api = MultiLoginAPIProvider.getApi();
    PlayerServiceData playerServiceData = (PlayerServiceData) data;
    playerServiceData.setServiceID(
        Objects.requireNonNull(api.getPlayerData(playerServiceData.getUuid()))
            .getLoginService()
            .getServiceId());
    playerServiceData.setServiceName(
        Objects.requireNonNull(api.getPlayerData(playerServiceData.getUuid()))
            .getLoginService()
            .getServiceName());
    PluginMessage message = new PluginMessage();
    message.setData(playerServiceData);
    server.sendPluginMessage(PluginMessageListener.getInstance().IDENTIFIER, message.toBytes());
  }
}
