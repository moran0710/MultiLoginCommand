package top.molab.minecraft.mLCommand.bukkit.listeners;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import top.molab.minecraft.MLCommand.Core.Constants;
import top.molab.minecraft.MLCommand.Core.pluginMessage.PluginMessage;
import top.molab.minecraft.mLCommand.bukkit.MLCommandBukkit;
import top.molab.minecraft.mLCommand.bukkit.pluginMessageHandler.IHandler;

public class MLCPluginMessageListener implements PluginMessageListener {

  private static volatile MLCPluginMessageListener instance;
  private List<IHandler> handlers = new ArrayList<>();

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

  public void registerHandler(IHandler handler) {
    handlers.add(handler);
  }

  @Override
  public void onPluginMessageReceived(String channel, Player usslessPlayer, byte[] bytes) {
    if (!channel.equals(Constants.PLUGIN_MESSAGE_CHANNEL)) {
      return;
    }

    PluginMessage message = PluginMessage.fromBytes(bytes);
    for (IHandler handler : handlers) {
      if (handler.canHandle(message.getMessageType())) {
        MLCommandBukkit.getInstance().getLogger().info("Handle "+message.toJson());
        handler.handle(message, usslessPlayer);
      }
    }
  }
}
