package top.molab.minecraft.mLCommand.bukkit.pluginMessageHandler;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import top.molab.minecraft.MLCommand.Core.DTO.PlayerLoginData;
import top.molab.minecraft.MLCommand.Core.pluginMessage.MessageTypes;
import top.molab.minecraft.MLCommand.Core.pluginMessage.PluginMessage;
import top.molab.minecraft.mLCommand.bukkit.utils.SendPluginMessageUtils;

public class GetLoginDataRequestHandler implements IHandler{


    @Override
    public boolean canHandle(MessageTypes type) {
        return type.equals(MessageTypes.GET_PLAYER_LOGIN_INFO);
    }

    @Override
    public void handle(PluginMessage pluginMessage, Player _player) {
        PlayerLoginData replyData = (PlayerLoginData) pluginMessage.getData();

        OfflinePlayer player = Bukkit.getOfflinePlayer(replyData.getUuid());
        replyData.setFirstLogin(!player.hasPlayedBefore());

        PluginMessage reply = new PluginMessage();
        reply.setMessageType(MessageTypes.REPLY_PLAYER_LOGIN_INFO);
        reply.setEcho(pluginMessage.getEcho());
        reply.setData(replyData);
        SendPluginMessageUtils.sendPluginMessage(reply.toBytes());
    }
}
