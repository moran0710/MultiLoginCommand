package top.molab.minecraft.mLCommand.bukkit.pluginMessageHandler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import top.molab.minecraft.MLCommand.Core.DTO.PlayerData;
import top.molab.minecraft.MLCommand.Core.config.Group;
import top.molab.minecraft.MLCommand.Core.pluginMessage.MessageTypes;
import top.molab.minecraft.MLCommand.Core.pluginMessage.PluginMessage;
import top.molab.minecraft.MLCommand.Core.utils.FindGroupUtils;
import top.molab.minecraft.mLCommand.bukkit.ConfigManager;
import top.molab.minecraft.mLCommand.bukkit.utils.CommandExecuteUtil;

public class GetReplyHandler implements IHandler {

  @Override
  public boolean canHandle(MessageTypes type) {
    return type.equals(MessageTypes.REPLY_PLAYER_DATA);
  }

  @Override
  public void handle(PluginMessage pluginMessage, Player player) {
    PlayerData data = (PlayerData) pluginMessage.getData();
    Group group =
        FindGroupUtils.findGroup(
            ConfigManager.getInstance().getConfig().getGroups(),
            data.getPlayerServiceData().getServiceID());
    if (group == null) {
      return;
    }

    if (data.getPlayerLoginData().isFirstLogin()) {
      Player joinPlayer = Bukkit.getPlayer(data.getPlayerServiceData().getUuid());
      for (String command : group.getFirstJoinCommands()) {
        CommandExecuteUtil.executeCommand(joinPlayer, command);
      }
    }
  }
}
