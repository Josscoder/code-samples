package josscoder.staffchat.command;

import josscoder.staffchat.StaffChatPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StaffChatCommand implements CommandExecutor {

  @Override
  public boolean onCommand(
    CommandSender sender,
    Command command,
    String label,
    String[] args
  ) {
    if (!sender.hasPermission(command.getPermission())) {
      sender.sendMessage(command.getPermissionMessage());
      return true;
    }

    if (args.length <= 0) {
      sender.sendMessage(command.getUsage());
      return true;
    }

    String message = String.join(" ", args);

    sender
      .getServer()
      .getOnlinePlayers()
      .stream()
      .filter(player -> player.hasPermission(command.getPermission()))
      .forEach(player ->
        player.sendMessage(
          StaffChatPlugin.PREFIX +
          ChatColor.GRAY +
          " " +
          sender.getName() +
          ": " +
          ChatColor.RESET +
          message
        )
      );

    return true;
  }
}
