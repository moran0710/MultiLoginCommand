package top.molab.minecraft.mlCommand.PluginMessage.handlers;

import com.velocitypowered.api.proxy.messages.ChannelMessageSink;
import top.molab.minecraft.MLCommand.Core.pluginMessage.MessageTypes;
import top.molab.minecraft.MLCommand.Core.pluginMessage.PluginMessage;
import top.molab.minecraft.mlCommand.PluginMessage.PluginMessageListener;

public class GetLoginDataReplyHandler implements MessageHandler{

    @Override
    public boolean canHandle(MessageTypes type) {
        return type.equals(MessageTypes.REPLY_PLAYER_LOGIN_INFO);
    }

    @Override
    public void handle(ChannelMessageSink server, PluginMessage message) {
        PluginMessageListener.getInstance().addPlayerLoginDataReply(message);
        PluginMessageListener.getInstance().checkTasks();
    }
}
