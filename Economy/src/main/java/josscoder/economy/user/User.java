package josscoder.economy.user;

import java.util.UUID;
import josscoder.economy.EconomyPlugin;
import josscoder.economy.provider.IProvider;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class User {

  private final UUID uniqueUid;

  @Getter
  @Setter
  private int money;

  public User(UUID uniqueUid) {
    this.uniqueUid = uniqueUid;
  }

  public void load() {
    IProvider provider = EconomyPlugin.getInstance().getProvider();

    if (!provider.contains(uniqueUid.toString())) {
      provider.createAccount(uniqueUid.toString());
    } else {
      money = provider.getUserCoins(uniqueUid.toString());
    }
  }

  public Player getPlayer() {
    return Bukkit.getPlayerExact(uniqueUid.toString());
  }

  public void increaseMoney() {
    addMoney(1);
  }

  public void addMoney(int amount) {
    money += amount;
  }

  public void decreaseMoney() {
    removeMoney(1);
  }

  public void removeMoney(int amount) {
    money = Math.max(0, (money - amount));
  }

  public void save() {
    IProvider provider = EconomyPlugin.getInstance().getProvider();

    if (provider.contains(uniqueUid.toString())) {
      provider.set(uniqueUid.toString(), money);
    }
  }
}
