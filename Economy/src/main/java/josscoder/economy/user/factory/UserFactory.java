package josscoder.economy.user.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import josscoder.economy.EconomyPlugin;
import josscoder.economy.user.User;
import org.bukkit.entity.Player;

public class UserFactory {

  private final Map<UUID, User> storage = new HashMap<>();

  public boolean contains(Player player) {
    return contains(player.getUniqueId());
  }

  public boolean contains(UUID uuid) {
    return storage.containsKey(uuid);
  }

  public void add(Player player) {
    storage.put(player.getUniqueId(), new User(player.getUniqueId()));
  }

  public User get(Player player) {
    return get(player.getUniqueId());
  }

  public User get(UUID uuid) {
    return storage.get(uuid);
  }

  public void remove(Player player) {
    storage.remove(player.getUniqueId());
  }

  public User getOffline(UUID uuid) {
    return new User(uuid);
  }
}
