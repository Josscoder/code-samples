package josscoder.economy.user;

import java.util.UUID;
import josscoder.economy.EconomyPlugin;
import josscoder.economy.event.EconomyChangeEvent;
import josscoder.economy.provider.IProvider;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class User {

  private final UUID uniqueUid;
  private final EconomyPlugin plugin;

  @Getter
  @Setter
  private int money = 0;

  public User(UUID uniqueUid) {
    this.uniqueUid = uniqueUid;
    this.plugin = EconomyPlugin.getInstance();

    EconomyPlugin.getInstance().scheduleAsync(this::load);
  }

  public User load() {
    IProvider provider = plugin.getProvider();

    if (!provider.contains(uniqueUid.toString())) {
      provider.createAccount(uniqueUid.toString());
    } else {
      money = provider.getUserCoins(uniqueUid.toString());
    }

    return this;
  }

  public Player getPlayer() {
    return Bukkit.getPlayerExact(uniqueUid.toString());
  }

  public void increaseMoney() {
    addMoney(1);
  }

  public User addMoney(int amount) {
    int result = (money += amount);

    if (
      plugin
        .callEvent(new EconomyChangeEvent(getPlayer(), money, result))
        .isCancelled()
    ) {
      return this;
    }

    money = result;

    return this;
  }

  public User decreaseMoney() {
    removeMoney(1);

    return this;
  }

  public User removeMoney(int amount) {
    int result = Math.max(0, (money - amount));

    if (
      plugin
        .callEvent(new EconomyChangeEvent(getPlayer(), money, result))
        .isCancelled()
    ) {
      return this;
    }

    money = result;

    return this;
  }

  public User save() {
    IProvider provider = plugin.getProvider();

    if (provider.contains(uniqueUid.toString())) {
      provider.set(uniqueUid.toString(), money);
    }

    return this;
  }
}
