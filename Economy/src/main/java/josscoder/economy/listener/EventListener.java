package josscoder.economy.listener;

import josscoder.economy.EconomyPlugin;
import josscoder.economy.user.User;
import josscoder.economy.user.factory.UserFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class EventListener implements Listener {

  private final UserFactory userFactory;

  public EventListener() {
    userFactory = EconomyPlugin.getInstance().getUserFactory();
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();

    userFactory.add(player);
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();

    User user = userFactory.get(player);

    if (user == null) {
      return;
    }

    EconomyPlugin
      .getInstance()
      .scheduleAsync(
        new BukkitRunnable() {
          @Override
          public void run() {
            user.save();
            userFactory.remove(player);
          }
        }
      );
  }
}
