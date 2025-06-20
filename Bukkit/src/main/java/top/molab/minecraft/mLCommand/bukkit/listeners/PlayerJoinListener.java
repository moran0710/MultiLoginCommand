package top.molab.minecraft.mLCommand.bukkit.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;
import top.molab.minecraft.MLCommand.Core.Constants;
import top.molab.minecraft.MLCommand.Core.DTO.PlayerServiceData;
import top.molab.minecraft.MLCommand.Core.pluginMessage.MessageTypes;
import top.molab.minecraft.MLCommand.Core.pluginMessage.PluginMessage;
import top.molab.minecraft.MLCommand.Core.utils.StringUtils;
import top.molab.minecraft.mLCommand.bukkit.ConfigManager;
import top.molab.minecraft.mLCommand.bukkit.MLCommandBukkit;

public class PlayerJoinListener implements Listener {

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event){
    new BukkitRunnable(){
      public void run(){
        Player player = event.getPlayer();
        PlayerServiceData data = new PlayerServiceData();
        data.setName(player.getName());
        data.setUuid(player.getUniqueId());
        data.setServiceName("");

        // 请求获取玩家组信息
        // 会在GetReplyHandler中处理
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
        // getPlayerDataMessage.toBytes());
        MLCommandBukkit.getInstance().getLogger().info("Sent plugin message: "+ getPlayerDataMessage.toJson());
      }
    }.runTaskLater(MLCommandBukkit.getInstance(), 30);
  }

}
