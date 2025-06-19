package top.molab.minecraft.mlCommand;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.messages.ChannelMessageSink;
import top.molab.minecraft.MLCommand.Core.DTO.PlayerData;
import top.molab.minecraft.MLCommand.Core.DTO.PlayerLoginData;
import top.molab.minecraft.MLCommand.Core.DTO.PlayerServiceData;
import top.molab.minecraft.MLCommand.Core.pluginMessage.MessageTypes;
import top.molab.minecraft.MLCommand.Core.pluginMessage.PluginMessage;
import top.molab.minecraft.MLCommand.Core.utils.StringUtils;
import top.molab.minecraft.mlCommand.PluginMessage.PluginMessageListener;

import java.util.ArrayList;
import java.util.List;

public class Task {
    private List<PlayerLoginData> reply = new ArrayList<>();
    private int serverCount;
    private String echo;
    private ChannelMessageSink channel;
    private PlayerServiceData serviceData;

    public Task(int serverCount, String echo, ChannelMessageSink channel, PlayerServiceData serviceData) {
        this.serverCount = serverCount;
        this.echo = echo;
        this.channel = channel;
        this.serviceData = serviceData;
    }

    public boolean isReplyMatch(String echo){
        return this.echo.equals(echo);
    }

    public void addReply(PlayerLoginData data) {
        reply.add(data);
    }

    public boolean isFinished() {
        return reply.size() == this.serverCount;
    }

    public void sendReply(){
        PlayerLoginData loginData = this.getResult();

        PlayerData data = new PlayerData();
        data.setPlayerLoginData(loginData);
        data.setPlayerServiceData(this.serviceData);
        PluginMessage message = new PluginMessage();
        message.setMessageType(MessageTypes.REPLY_PLAYER_DATA);
        message.setData(data);
        message.setEcho(StringUtils.getRandomEchoString());
        this.channel.sendPluginMessage(PluginMessageListener.getInstance().IDENTIFIER, message.toBytes());
    }

    public PlayerLoginData getResult(){
        PlayerLoginData result = new PlayerLoginData();
        result.setFirstLogin(true);
        result.setName(reply.get(0).getName());
        result.setUuid(reply.get(0).getUuid());
        for (PlayerLoginData data: this.reply){
            if (data.isFirstLogin()){
                return data;
            }
        }
        return result;
    }
}
