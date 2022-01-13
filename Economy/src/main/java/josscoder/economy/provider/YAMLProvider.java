package josscoder.economy.provider;

import java.io.File;
import java.io.IOException;
import josscoder.economy.EconomyPlugin;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class YAMLProvider extends Provider {

  private File file;
  private FileConfiguration config;

  public YAMLProvider init() {
    EconomyPlugin plugin = EconomyPlugin.getInstance();

    file = new File(plugin.getDataFolder(), "users.yml");

    if (!file.exists()) {
      file.getParentFile().mkdirs();
      plugin.saveResource("users.yml", false);
    }

    config = new YamlConfiguration();

    try {
      config.load(file);
    } catch (IOException | InvalidConfigurationException e) {
      e.printStackTrace();
    }

    return this;
  }

  @Override
  public String getName() {
    return "YAML";
  }

  @Override
  public boolean contains(String uniqueUid) {
    return config.contains(uniqueUid);
  }

  @Override
  public void createAccount(String uniqueUid, int amount) {
    config.set(uniqueUid, amount);
  }

  @Override
  public int getUserCoins(String uniqueUid) {
    return config.getInt(uniqueUid, 0);
  }

  @Override
  public void set(String uniqueUid, int amount) {
    config.set(uniqueUid, amount);

    try {
      config.save(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void close() {}
}
