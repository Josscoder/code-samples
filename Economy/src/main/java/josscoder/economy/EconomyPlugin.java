package josscoder.economy;

import java.util.UUID;
import josscoder.economy.command.TestEconomyCommand;
import josscoder.economy.listener.EventListener;
import josscoder.economy.provider.IProvider;
import josscoder.economy.provider.MySQLProvider;
import josscoder.economy.provider.YAMLProvider;
import josscoder.economy.user.User;
import josscoder.economy.user.factory.UserFactory;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class EconomyPlugin extends JavaPlugin {

  @Getter
  private static EconomyPlugin instance;

  private IProvider provider = null;

  private UserFactory userFactory;

  @Override
  public void onLoad() {
    instance = this;
  }

  @Override
  public void onEnable() {
    saveDefaultConfig();

    if (!initProvider()) {
      getPluginLoader().disablePlugin(this);

      return;
    }

    userFactory = new UserFactory();

    getServer()
      .getPluginCommand("testeconomy")
      .setExecutor(new TestEconomyCommand());
    getServer().getPluginManager().registerEvents(new EventListener(), this);

    info(ChatColor.GREEN + "This plugin has been enabled!");
  }

  public void info(String message) {
    getServer()
      .getConsoleSender()
      .sendMessage(ChatColor.GRAY + "[" + getName() + "] " + message);
  }

  private boolean initProvider() {
    FileConfiguration config = getConfig();

    ConfigurationSection section = config.getConfigurationSection("provider");

    String providerType = section.getString("type");

    switch (providerType.toLowerCase()) {
      case "mysql":
        ConfigurationSection mysqlSection = section.getConfigurationSection(
          "mysql"
        );
        provider =
          new MySQLProvider()
            .init(
              mysqlSection.getString("host"),
              mysqlSection.getInt("port"),
              mysqlSection.getString("username"),
              mysqlSection.getString("password"),
              mysqlSection.getString("database")
            );
        break;
      case "yaml":
        provider = new YAMLProvider().init();
        break;
    }

    if (provider == null) {
      info(
        ChatColor.RED +
        "Failed to load a provider, that's provider does not exist"
      );

      return false;
    }

    info(ChatColor.GOLD + "The provider is " + provider.getName() + "!");

    return true;
  }

  public User user(UUID uuid) {
    return (
      !userFactory.contains(uuid)
        ? userFactory.getOffline(uuid)
        : userFactory.get(uuid)
    );
  }

  public void scheduleAsync(Runnable runnable) {
    Bukkit.getScheduler().scheduleSyncDelayedTask(this, runnable, 1);
  }

  @Override
  public void onDisable() {
    provider.close();

    info(ChatColor.RED + "This plugin has been disabled!");
  }

  public <T extends Event> T callEvent(T event) {
    getServer().getPluginManager().callEvent(event);

    return event;
  }
}
