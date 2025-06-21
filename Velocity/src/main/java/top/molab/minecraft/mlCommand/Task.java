package top.molab.minecraft.mlCommand;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import java.util.ArrayList;
import java.util.List;
import top.molab.minecraft.MLCommand.Core.DTO.PlayerData;
import top.molab.minecraft.MLCommand.Core.DTO.PlayerLoginData;
import top.molab.minecraft.MLCommand.Core.DTO.PlayerServiceData;
import top.molab.minecraft.MLCommand.Core.pluginMessage.MessageTypes;
import top.molab.minecraft.MLCommand.Core.pluginMessage.PluginMessage;
import top.molab.minecraft.MLCommand.Core.utils.StringUtils;
import top.molab.minecraft.mlCommand.PluginMessage.PluginMessageListener;

public class Task {
  private List<PlayerLoginData> reply = new ArrayList<>();
  private int serverCount;
  private String echo;
  private String serverName;
  private PlayerServiceData serviceData;

  public Task(int serverCount, String echo, String serverName, PlayerServiceData serviceData) {
    this.serverCount = serverCount;
    this.echo = echo;
    this.serverName = serverName;
    this.serviceData = serviceData;
  }

  // 用echo检查是不是同一个任务的响应
  public boolean isReplyMatch(String echo) {
    return this.echo.equals(echo);
  }

  // 加入回复
  public void addReply(PlayerLoginData data) {
    reply.add(data);
  }

  // 如果回复数量=服务器数量那就是全部回复完了
  // 可以调用sendReply去结束回调地狱了
  public boolean isFinished() {
    return reply.size() == this.serverCount;
  }

  // 上帝啊，你终于指引我离开回调地狱了
  public void sendReply() {
    PlayerLoginData loginData = this.getResult();

    PlayerData data = new PlayerData();
    data.setPlayerLoginData(loginData);
    data.setPlayerServiceData(this.serviceData);
    PluginMessage message = new PluginMessage();
    message.setMessageType(MessageTypes.REPLY_PLAYER_DATA);
    message.setData(data);
    message.setEcho(StringUtils.getRandomEchoString());

    // 发出去，再也不管了
    for (RegisteredServer s : MLCommandVelocity.getInstance().getProxyServer().getAllServers()) {
      if (s.getServerInfo().getName().equals(this.serverName)) {
        s.sendPluginMessage(PluginMessageListener.getInstance().IDENTIFIER, message.toBytes());
        return;
      }
    }
    // this.channel.sendPluginMessage(PluginMessageListener.getInstance().IDENTIFIER,
    // message.toBytes());
  }

  public PlayerLoginData getResult() {
    PlayerLoginData result = new PlayerLoginData();
    result.setFirstLogin(false);
    result.setName(reply.get(0).getName());
    result.setUuid(reply.get(0).getUuid());
    for (PlayerLoginData data : this.reply) {
      if (data.isFirstLogin()) {
        return data;
      }
    }
    return result;
  }
}
