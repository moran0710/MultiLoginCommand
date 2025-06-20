package top.molab.minecraft.mlCommand;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import java.nio.file.Path;
import org.slf4j.Logger;
import top.molab.minecraft.mlCommand.PluginMessage.PluginMessageListener;
import top.molab.minecraft.mlCommand.PluginMessage.handlers.GetLoginDataReplyHandler;
import top.molab.minecraft.mlCommand.PluginMessage.handlers.PlayerDataHandler;

@Plugin(
    id = "mlcommand-velocity",
    name = "MultiLoginCommand",
    version = "0.0.1-SNAPSHOT",
    url = "openmo.molab.top",
    description = "Execute command with Player Login by MultiLogin",
    dependencies = {@Dependency(id = "multilogin")},
    authors = {"Moran0710"})
public class MLCommandVelocity {

  private static MLCommandVelocity instance;
  private final ProxyServer proxyServer;
  private final Logger logger;
  private final Path dataDictionary;

  @Inject
  public MLCommandVelocity(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
    instance = this;
    this.proxyServer = server;
    this.logger = logger;
    this.dataDictionary = dataDirectory;

    logger.info("----------------------------------");
    logger.info("MultiLoginCommand-Velocity Started");
    logger.info("----------------------------------");

    ConfigManager.getInstance().init();
  }

  public static MLCommandVelocity getInstance() {
    return instance;
  }

  @Subscribe
  public void onInitialize(ProxyInitializeEvent event) {
    proxyServer.getChannelRegistrar().register(PluginMessageListener.getInstance().IDENTIFIER);
    PluginMessageListener.getInstance().registerHandler(new PlayerDataHandler());
    PluginMessageListener.getInstance().registerHandler(new GetLoginDataReplyHandler());
    this.proxyServer.getEventManager().register(this, PluginMessageListener.getInstance());
  }

  public ProxyServer getProxyServer() {
    return proxyServer;
  }

  public Logger getLogger() {
    return logger;
  }

  public Path getDataDictionary() {
    return dataDictionary;
  }

}
