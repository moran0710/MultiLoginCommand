package top.molab.minecraft.mlCommand.PluginMessage.handlers;

import com.velocitypowered.api.proxy.messages.ChannelMessageSink;
import top.molab.minecraft.MLCommand.Core.pluginMessage.MessageTypes;

public interface MessageHandler {
  public boolean canHandle(MessageTypes type);

  public void handle(ChannelMessageSink server, Object data);
}
