package josscoder.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class PacketsTest extends JavaPlugin {

  @Getter
  private static PacketsTest instance;

  @Getter
  private ProtocolManager protocolManager;

  @Override
  public void onLoad() {
    instance = this;
    protocolManager = ProtocolLibrary.getProtocolManager();
  }

  @Override
  public void onEnable() {
    protocolManager.addPacketListener(
      new PacketAdapter(
        this,
        ListenerPriority.NORMAL,
        PacketType.Play.Server.NAMED_SOUND_EFFECT
      ) {
        @Override
        public void onPacketSending(PacketEvent event) {
          if (
            event.getPacketType() == PacketType.Play.Server.NAMED_SOUND_EFFECT
          ) {
            event.setCancelled(true);
          }
        }
      }
    );

    protocolManager.addPacketListener(
      new PacketAdapter(
        this,
        ListenerPriority.NORMAL,
        PacketType.Play.Client.CHAT
      ) {
        @Override
        public void onPacketReceiving(PacketEvent event) {
          if (event.getPacketType() == PacketType.Play.Client.CHAT) {
            PacketContainer packet = event.getPacket();
            String message = packet.getStrings().read(0);

            if (message.contains("shit") || message.contains("damn")) {
              event.setCancelled(true);
              event.getPlayer().sendMessage("Bad manners!");
            }
          }
        }
      }
    );
  }

  @Override
  public void onDisable() {}
}
