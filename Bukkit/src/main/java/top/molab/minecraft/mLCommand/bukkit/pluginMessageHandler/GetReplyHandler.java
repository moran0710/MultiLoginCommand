package top.molab.minecraft.mLCommand.bukkit.pluginMessageHandler;

import com.google.gson.internal.LinkedTreeMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import top.molab.minecraft.MLCommand.Core.DTO.PlayerData;
import top.molab.minecraft.MLCommand.Core.config.Group;
import top.molab.minecraft.MLCommand.Core.pluginMessage.MessageTypes;
import top.molab.minecraft.MLCommand.Core.pluginMessage.PluginMessage;
import top.molab.minecraft.MLCommand.Core.utils.ClassCastUtil;
import top.molab.minecraft.MLCommand.Core.utils.FindGroupUtils;
import top.molab.minecraft.mLCommand.bukkit.ConfigManager;
import top.molab.minecraft.mLCommand.bukkit.utils.CommandExecuteUtil;

/** 在Velocity完成收集完全部内容后，会发送MessageTypes.REPLY_PLAYER_DATA，这时候可以处理登陆命令了 */
public class GetReplyHandler implements IHandler {

  @Override
  public boolean canHandle(MessageTypes type) {
    return type.equals(MessageTypes.REPLY_PLAYER_DATA);
  }

  @Override
  public void handle(PluginMessage pluginMessage, Player player) {
    PlayerData data =
        ClassCastUtil.getPlayerDataFromLickedTreeMap((LinkedTreeMap) pluginMessage.getData());
    Group group =
        FindGroupUtils.findGroup(
            ConfigManager.getInstance().getConfig().getGroups(),
            data.getPlayerServiceData().getServiceID());
    if (group == null) {
      return;
    }
    Player joinPlayer = Bukkit.getPlayer(data.getPlayerServiceData().getUuid());
    if (data.getPlayerLoginData().isFirstLogin()) {

      for (String command : group.getFirstJoinCommands()) {
        CommandExecuteUtil.executeCommand(joinPlayer, command);
      }
    } else {
      for (String command : group.getCommands()) {
        CommandExecuteUtil.executeCommand(joinPlayer, command);
      }
    }
  }
}
