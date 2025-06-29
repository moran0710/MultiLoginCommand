package top.molab.minecraft.mLCommand.bukkit.pluginMessageHandler;

import com.google.gson.internal.LinkedTreeMap;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import top.molab.minecraft.MLCommand.Core.DTO.PlayerLoginData;
import top.molab.minecraft.MLCommand.Core.pluginMessage.MessageTypes;
import top.molab.minecraft.MLCommand.Core.pluginMessage.PluginMessage;
import top.molab.minecraft.MLCommand.Core.utils.ClassCastUtil;
import top.molab.minecraft.mLCommand.bukkit.utils.SendPluginMessageUtils;

/** 在Velocity请求了解玩家是否首次登陆的时候，发送回去消息，会在那边被收集进Task再处理 Task完成会发送玩家完整登录信息，GetReplyHandler处理 */
public class GetLoginDataRequestHandler implements IHandler {

  @Override
  public boolean canHandle(MessageTypes type) {
    return type.equals(MessageTypes.GET_PLAYER_LOGIN_INFO);
  }

  @Override
  public void handle(PluginMessage pluginMessage, Player _player) {
    PlayerLoginData replyData =
        ClassCastUtil.getPlayerLoginDataFromLinkedTreeMap((LinkedTreeMap) pluginMessage.getData());

    OfflinePlayer player = Bukkit.getOfflinePlayer(replyData.getUuid());
    replyData.setFirstLogin(!player.hasPlayedBefore());

    PluginMessage reply = new PluginMessage();
    reply.setMessageType(MessageTypes.REPLY_PLAYER_LOGIN_INFO);
    reply.setEcho(pluginMessage.getEcho());
    reply.setData(replyData);
    SendPluginMessageUtils.sendPluginMessage(reply.toBytes());
  }
}
