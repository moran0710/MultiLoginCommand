package top.molab.minecraft.mlCommand;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import org.slf4j.Logger;
import top.molab.minecraft.MLCommand.Core.config.Config;

public class ConfigManager {

  private static volatile ConfigManager instance;
  private Config config;

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

  public void init() {
    Path dataDictionary = MLCommandVelocity.getInstance().getDataDictionary();
    Logger logger = MLCommandVelocity.getInstance().getLogger();
    File configFile = new File(dataDictionary.toFile(), "config.yml");
    try {
      this.config = Config.getConfig(configFile);
      // logger.info(config.getGroups()[0].getGroupName());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Config getConfig() {
    return config;
  }
}
