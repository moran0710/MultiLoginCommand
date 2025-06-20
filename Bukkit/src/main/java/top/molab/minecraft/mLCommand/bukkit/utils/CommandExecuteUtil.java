package top.molab.minecraft.mLCommand.bukkit.utils;

import cc.carm.lib.easyplugin.utils.ColorParser;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import top.molab.minecraft.mLCommand.bukkit.ConfigManager;
import top.molab.minecraft.mLCommand.bukkit.MLCommandBukkit;

public class CommandExecuteUtil {

  /**
   * 解析配置文件中的命令 支持以下命令： 1. player: 以玩家自己的身份执行一个命令 2. console: 以控制台的身份执行一个命令 3. op: 将玩家提权为管理，再执行一个命令
   * 4. tell: 向玩家发送消息 5. broadcast: 向所有玩家发送消息 支持PAPI变量。会先解析papi变量再执行 如果命令无法被解析，抛出RuntimeException
   *
   * @param player 要执行命令的玩家
   * @throws RuntimeException 命令无法被解析时抛出
   */
  public static void executeCommand(Player player, String rawCmd) {
    String[] temp = rawCmd.split(":", 2);
    String type = temp[0];
    String command = PlaceholderAPI.setPlaceholders(player, temp[1]);
    MLCommandBukkit.getInstance().getLogger().info("execute command by "+player.getName()+": "+rawCmd);

    switch (type) {
      case "player":
        executeCommandAsPlayer(player, command);
        break;
      case "console":
        executeCommandAsConsole(command);
        break;
      case "op":
        executeCommandAsOp(player, command);
        break;
      case "tell":
        command = ColorParser.parse(command);
        tellPlayer(player, command);
        break;
      case "broadcast":
        command = ColorParser.parse(command);
        broadcast(command);
        break;
      default:
        throw new RuntimeException("Unknown command type:" + type + ", full command:" + rawCmd);
    }
  }

  /**
   * 以玩家自己的身份执行一个命令
   *
   * @param player 执行命令的玩家
   * @param command 命令
   */
  public static void executeCommandAsPlayer(Player player, String command) {
    player.performCommand(command);
  }

  /**
   * 以控制台的身份执行一个命令
   *
   * @param command 命令
   */
  public static void executeCommandAsConsole(String command) {
    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
  }

  /**
   * 让玩家以OP身份执行命令
   *
   * @param player 玩家
   * @param command 命令
   */
  public static void executeCommandAsOp(Player player, String command) {
    if (player.isOp()){
      executeCommandAsPlayer(player, command);
    }else{
    player.setOp(true);
    player.performCommand(command);
    player.setOp(false);
  }}

  /**
   * 向玩家发送消息
   *
   * @param player 玩家
   * @param message 消息
   */
  public static void tellPlayer(Player player, String message) {
    player.sendMessage(message);
  }

  /**
   * 向所有玩家发送消息
   *
   * @param message 消息
   */
  public static void broadcast(String message) {
    Bukkit.broadcastMessage(message);
  }
}
