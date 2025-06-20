package top.molab.minecraft.mlCommand.PluginMessage;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.google.gson.internal.LinkedTreeMap;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import top.molab.minecraft.MLCommand.Core.Constants;
import top.molab.minecraft.MLCommand.Core.DTO.PlayerLoginData;
import top.molab.minecraft.MLCommand.Core.pluginMessage.PluginMessage;
import top.molab.minecraft.MLCommand.Core.utils.ClassCastUtil;
import top.molab.minecraft.mlCommand.MLCommandVelocity;
import top.molab.minecraft.mlCommand.PluginMessage.handlers.MessageHandler;
import top.molab.minecraft.mlCommand.Task;

public class PluginMessageListener {
  private static volatile PluginMessageListener instance;
  public final MinecraftChannelIdentifier IDENTIFIER =
      MinecraftChannelIdentifier.from(Constants.PLUGIN_MESSAGE_CHANNEL);
  private final List<MessageHandler> handlers = new ArrayList<>();
  private final List<Task> tasks = new ArrayList<>();

  public void addTask(Task task){
    tasks.add(task);
  }

  // 把玩家登录状态的数据装回到Tasks里面
  public void addPlayerLoginDataReply(PluginMessage message){
    for (Task task: tasks){
      if (task.isReplyMatch(message.getEcho())){
        task.addReply(ClassCastUtil.getPlayerLoginDataFromLinkedTreeMap((LinkedTreeMap)message.getData()));
      }
    }
  }

  public void removeTask(Task task){
    tasks.remove(task);
  }

  private PluginMessageListener() {}

  public static PluginMessageListener getInstance() {
    if (instance == null) {
      synchronized (PluginMessageListener.class) {
        if (instance == null) {
          instance = new PluginMessageListener();
        }
      }
    }
    return instance;
  }

  public void registerHandler(MessageHandler handler) {
    handlers.add(handler);
    MLCommandVelocity.getInstance()
        .getLogger()
        .info("Registered handler: {}", handler.getClass().getName());
  }

  // 检查哪些任务完成了
  // 完成了就发掉消息结束回调地狱
  // 这特么一大坨屎终于结束了
  public void checkTasks(){
    List<Task> removed = new ArrayList<>();
    for (Task task: tasks){
      if (task.isFinished()){
        task.sendReply();
        removed.add(task);
      }
    }
    synchronized (tasks){
      for (Task task: removed){
        tasks.remove(task);
      }
    }
    // MLCommandVelocity.getInstance().getLogger().info("");
  }

  @Subscribe
  public void onPluginMessage(PluginMessageEvent event) {
    if (!IDENTIFIER.equals(event.getIdentifier())) {
      return;
    }
    event.setResult(PluginMessageEvent.ForwardResult.handled());

    ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
    var s = in.readUTF();
    PluginMessage pluginMessage = PluginMessage.fromJson(s);
    for (MessageHandler handler : handlers) {
      if (handler.canHandle(pluginMessage.getMessageType())) {
        handler.handle(event.getTarget(), pluginMessage);
      }
    }
  }
}
