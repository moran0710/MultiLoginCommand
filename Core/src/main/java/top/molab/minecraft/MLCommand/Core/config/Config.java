package top.molab.minecraft.MLCommand.Core.config;

import java.io.*;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

public class Config {
  private Group[] groups;
  private int serverCount;
  private int delay;

  public Config() {}

  public Config(Group[] groups) {
    this.groups = groups;
  }

  public static Config saveDefaultConfig(File file) throws IOException {

    Group[] groups =
        new Group[] {
          new Group(1, "Offical", new String[] {"player: help"}, new String[] {"player: help"}),
        };
    Config config = new Config(groups);
    config.setDelay(30);
    config.setServerCount(1);
    file.getParentFile().mkdir();
    file.createNewFile();

    try (StringWriter writer = new StringWriter()) {

      DumperOptions options = new DumperOptions();
      options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

      Representer representer = new Representer(options);
      representer.addClassTag(Config.class, Tag.MAP);  // 明确指定配置类标签

      Yaml yaml = new Yaml(representer,options);
      yaml.represent(config);
      yaml.dump(config, writer);
      try (FileWriter fileWriter = new FileWriter(file)) {
        fileWriter.write(writer.toString());
      }
    }
    return config;
  }

  public static Config getConfig(File file) throws IOException {
    Config config;
    if (!file.exists()) {
      try {
        config = saveDefaultConfig(file);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else {
      try (FileReader reader = new FileReader(file)) {
        Yaml yaml = new Yaml(new Constructor(Config.class, new LoaderOptions()));
        config = yaml.load(reader);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    }
    return config;
  }

  public Group[] getGroups() {
    return groups;
  }

  public void setGroups(Group[] groups) {
    this.groups = groups;
  }

    public int getServerCount() {
        return serverCount;
    }

    public void setServerCount(int serverCount) {
        this.serverCount = serverCount;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
