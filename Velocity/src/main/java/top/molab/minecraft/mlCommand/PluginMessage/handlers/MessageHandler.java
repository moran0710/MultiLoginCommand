package top.molab.minecraft.mlCommand.PluginMessage.handlers;

import com.velocitypowered.api.proxy.messages.ChannelMessageSink;
import top.molab.minecraft.MLCommand.Core.pluginMessage.MessageTypes;
import top.molab.minecraft.MLCommand.Core.pluginMessage.PluginMessage;

public interface MessageHandler {
  public boolean canHandle(MessageTypes type);
  public void handle(ChannelMessageSink server, PluginMessage data);
}
