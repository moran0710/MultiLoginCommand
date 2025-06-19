package top.molab.minecraft.mLCommand.bukkit.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import top.molab.minecraft.MLCommand.Core.Constants;
import top.molab.minecraft.MLCommand.Core.DTO.PlayerServiceData;
import top.molab.minecraft.MLCommand.Core.pluginMessage.MessageTypes;
import top.molab.minecraft.MLCommand.Core.pluginMessage.PluginMessage;
import top.molab.minecraft.MLCommand.Core.utils.StringUtils;
import top.molab.minecraft.mLCommand.bukkit.ConfigManager;
import top.molab.minecraft.mLCommand.bukkit.MLCommandBukkit;

public class PlayerJoinListener implements Listener {



  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    PlayerServiceData data = new PlayerServiceData();
    data.setName(player.getName());
    data.setUuid(player.getUniqueId());

    // 获取玩家组信息
    PluginMessage getPlayerDataMessage = new PluginMessage();
    getPlayerDataMessage.setData(data);
    getPlayerDataMessage.setMessageType(MessageTypes.GET_PLAYER_DATA);
    getPlayerDataMessage.setEcho(StringUtils.getRandomEchoString());

    event
        .getPlayer()
        .sendPluginMessage(
            MLCommandBukkit.getInstance(),
            Constants.PLUGIN_MESSAGE_CHANNEL,
            getPlayerDataMessage.toBytes());
    ConfigManager.getInstance().addTask(getPlayerDataMessage);
  }
}
