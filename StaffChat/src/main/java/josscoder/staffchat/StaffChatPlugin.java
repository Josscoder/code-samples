package josscoder.staffchat;

import josscoder.staffchat.command.StaffChatCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class StaffChatPlugin extends JavaPlugin {

  public static String PREFIX = ChatColor.BLUE + "[S]";

  @Override
  public void onEnable() {
    getServer()
      .getPluginCommand("staffchat")
      .setExecutor(new StaffChatCommand());

    ConsoleCommandSender sender = getServer().getConsoleSender();

    sender.sendMessage(ChatColor.GREEN + "This plugin has been enabled!");
  }

  @Override
  public void onDisable() {
    getServer()
      .getConsoleSender()
      .sendMessage(ChatColor.RED + "This plugin has been disabled!");
  }
}
