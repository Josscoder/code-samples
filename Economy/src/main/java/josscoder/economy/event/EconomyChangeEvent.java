package josscoder.economy.event;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class EconomyChangeEvent extends EconomyEvent {

  private final int lastMoney;
  private final int newMoney;

  public EconomyChangeEvent(Player who, int lastMoney, int newMoney) {
    super(who);
    this.lastMoney = lastMoney;
    this.newMoney = newMoney;
  }
}
