package josscoder.economy.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public abstract class EconomyEvent extends PlayerEvent implements Cancellable {

  private boolean cancel;

  private static final HandlerList handlers = new HandlerList();

  public EconomyEvent(Player who) {
    super(who);
  }

  @Override
  public boolean isCancelled() {
    return cancel;
  }

  @Override
  public void setCancelled(boolean cancel) {
    this.cancel = cancel;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }
}
