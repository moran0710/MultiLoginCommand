package top.molab.minecraft.mLCommand.bukkit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import top.molab.minecraft.MLCommand.Core.config.Config;
import top.molab.minecraft.MLCommand.Core.pluginMessage.PluginMessage;

public class ConfigManager {
  private static volatile ConfigManager instance;
  private Config config;
  private List<PluginMessage> tasks = new ArrayList<>();
  private boolean isPapiEnabled;

  private ConfigManager() {}

  public static ConfigManager getInstance() {
    if (instance == null) {
      synchronized (ConfigManager.class) {
        if (instance == null) {
          instance = new ConfigManager();
        }
      }
    }
    return instance;
  }

  public void addTask(PluginMessage task) {
    tasks.add(task);
  }

  public void removeTask(PluginMessage task) {
    tasks.remove(task);
  }

  public void removeTask(String echo) {
    tasks.removeIf(task -> task.getEcho().equals(echo));
  }

  public void init() {
    if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
      this.isPapiEnabled = false;
      MLCommandBukkit.getInstance()
          .getLogger()
          .warning("PAPI Not Found, PAPI Func Will Be Disabled");
    } else {
      this.isPapiEnabled = true;
    }

    Path dataDictionary = MLCommandBukkit.getInstance().getDataFolder().toPath();
    File configFile = new File(dataDictionary.toFile(), "config.yml");
    try {
      this.config = Config.getConfig(configFile);
    } catch (IOException e) {
      MLCommandBukkit.getInstance().getLogger().info("Config file Load Error");
      throw new RuntimeException(e);
    }
  }

  public Config getConfig() {
    return this.config;
  }

  public boolean isPapiEnabled() {
    return isPapiEnabled;
  }
}
