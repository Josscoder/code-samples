package josscoder.staffchat;

import josscoder.staffchat.command.StaffChatCommand;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class StaffChatPlugin extends JavaPlugin {

  public static String PREFIX = ChatColor.BLUE + "[S]";

  @Override
  public void onEnable() {
    getServer()
      .getPluginCommand("staffchat")
      .setExecutor(new StaffChatCommand());

    info(ChatColor.GREEN + "This plugin has been enabled!");
  }

  public void info(String message) {
    getServer()
      .getConsoleSender()
      .sendMessage(ChatColor.GRAY + "[" + getName() + "] " + message);
  }

  @Override
  public void onDisable() {
    info(ChatColor.RED + "This plugin has been disabled!");
  }
}
