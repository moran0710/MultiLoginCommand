package top.molab.minecraft.mLCommand.bukkit.pluginMessageHandler;

import org.bukkit.entity.Player;
import top.molab.minecraft.MLCommand.Core.pluginMessage.MessageTypes;
import top.molab.minecraft.MLCommand.Core.pluginMessage.PluginMessage;

public interface IHandler {
  public boolean canHandle(MessageTypes types);

  public void handle(PluginMessage pluginMessage, Player player);
}
