package josscoder.economy.command;

import josscoder.economy.EconomyPlugin;
import josscoder.economy.user.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestEconomyCommand implements CommandExecutor {

  @Override
  public boolean onCommand(
    CommandSender sender,
    Command command,
    String label,
    String[] args
  ) {
    if (!(sender instanceof Player)) {
      return true;
    }

    User user = EconomyPlugin
      .getInstance()
      .user(((Player) sender).getUniqueId());

    if (user == null) {
      return true;
    }

    sender.sendMessage("MY MONEY IS: $" + user.getMoney());
    user.increaseMoney();

    return true;
  }
}
