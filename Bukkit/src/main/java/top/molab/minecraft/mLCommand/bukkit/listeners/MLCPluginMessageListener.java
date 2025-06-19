package top.molab.minecraft.mLCommand.bukkit.listeners;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import top.molab.minecraft.MLCommand.Core.Constants;
import top.molab.minecraft.MLCommand.Core.pluginMessage.PluginMessage;
import top.molab.minecraft.mLCommand.bukkit.pluginMessageHandler.IHandler;

import java.util.ArrayList;
import java.util.List;

public class MLCPluginMessageListener implements PluginMessageListener {

  private static volatile MLCPluginMessageListener instance;
  private List<IHandler> handlers = new ArrayList<>();

  public void registerHandler(IHandler handler) {
    handlers.add(handler);
  }

  private MLCPluginMessageListener() {}

  public static MLCPluginMessageListener getInstance() {
    if (instance == null) {
      synchronized (PlayerJoinListener.class) {
        if (instance == null) {
          instance = new MLCPluginMessageListener();
        }
      }
    }
    return instance;
  }

  @Override
  public void onPluginMessageReceived(String channel, Player usslessPlayer, byte[] bytes) {
    if (!channel.equals(Constants.PLUGIN_MESSAGE_CHANNEL)) {
      return;
    }

    PluginMessage message = PluginMessage.fromBytes(bytes);
  }
}
