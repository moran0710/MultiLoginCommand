package top.molab.minecraft.mLCommand.bukkit;

import org.bukkit.plugin.java.JavaPlugin;
import top.molab.minecraft.MLCommand.Core.Constants;
import top.molab.minecraft.mLCommand.bukkit.listeners.MLCPluginMessageListener;
import top.molab.minecraft.mLCommand.bukkit.pluginMessageHandler.GetLoginDataRequestHandler;
import top.molab.minecraft.mLCommand.bukkit.pluginMessageHandler.GetReplyHandler;

public class MLCommandBukkit extends JavaPlugin {

  private static MLCommandBukkit instance;

  public static MLCommandBukkit getInstance() {
    return instance;
  }

  @Override
  public void onEnable() {
    instance = this;
    getLogger().info("========================");
    getLogger().info("MultiLoginCommand-Bukkit");
    getLogger().info("========================");
    getLogger().info("MultiLoginCommand, Version: " + getDescription().getVersion());

    getServer()
        .getMessenger()
        .registerOutgoingPluginChannel(this, Constants.PLUGIN_MESSAGE_CHANNEL);
    MLCPluginMessageListener.getInstance().registerHandler(new GetLoginDataRequestHandler());
    MLCPluginMessageListener.getInstance().registerHandler(new GetReplyHandler());
    getServer()
        .getMessenger()
        .registerIncomingPluginChannel(
            this, Constants.PLUGIN_MESSAGE_CHANNEL, MLCPluginMessageListener.getInstance());
    ConfigManager.getInstance().init();

    getLogger().info("MultiLoginCommand Load Successfully");
  }
}
