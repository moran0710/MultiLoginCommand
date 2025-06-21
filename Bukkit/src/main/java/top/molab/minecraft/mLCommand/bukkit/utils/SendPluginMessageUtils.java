package top.molab.minecraft.mLCommand.bukkit.utils;

import org.bukkit.Bukkit;
import top.molab.minecraft.MLCommand.Core.Constants;
import top.molab.minecraft.mLCommand.bukkit.MLCommandBukkit;

public class SendPluginMessageUtils {

  public static void sendPluginMessage(byte[] message) {
    Bukkit.getOnlinePlayers().stream().toList().get(0).sendPluginMessage(
            MLCommandBukkit.getInstance(),
            Constants.PLUGIN_MESSAGE_CHANNEL,
            message
    );
  }
}
