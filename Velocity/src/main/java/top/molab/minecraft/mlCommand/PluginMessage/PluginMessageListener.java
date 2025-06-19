package top.molab.minecraft.mlCommand.PluginMessage;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import java.util.ArrayList;
import java.util.List;
import top.molab.minecraft.MLCommand.Core.Constants;
import top.molab.minecraft.MLCommand.Core.pluginMessage.PluginMessage;
import top.molab.minecraft.mlCommand.MLCommandVelocity;
import top.molab.minecraft.mlCommand.PluginMessage.handlers.MessageHandler;

public class PluginMessageListener {
  private static volatile PluginMessageListener instance;
  public final MinecraftChannelIdentifier IDENTIFIER =
      MinecraftChannelIdentifier.from(Constants.PLUGIN_MESSAGE_CHANNEL);
  private final List<MessageHandler> handlers = new ArrayList<>();

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

  @Subscribe
  public void onPluginMessage(PluginMessageEvent event) {
    if (!IDENTIFIER.equals(event.getIdentifier())) {
      return;
    }
    event.setResult(PluginMessageEvent.ForwardResult.handled());
    ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
    PluginMessage pluginMessage = PluginMessage.fromJson(in.readUTF());
    for (MessageHandler handler : handlers) {
      if (handler.canHandle(pluginMessage.getMessageType())) {
        handler.handle(event.getTarget(), pluginMessage.getData());
      }
    }
  }
}
