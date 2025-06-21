package top.molab.minecraft.mLCommand.bukkit;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Command implements CommandExecutor {
  @Override
  public boolean onCommand(
      @NotNull CommandSender sender,
      @NotNull org.bukkit.command.Command command,
      @NotNull String label,
      @NotNull String[] args) {
    if (sender.hasPermission("mlcommand.reload")) {
      ConfigManager.getInstance().init();
      sender.sendMessage("MultiLoginCommand插件已经重载");
      return true;
    } else {
      sender.sendMessage("你没有权限使用此命令");
      return true;
    }
  }
}
