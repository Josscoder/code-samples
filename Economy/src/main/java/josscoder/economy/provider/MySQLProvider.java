package josscoder.economy.provider;

import com.code.advancedsql.MySQL;
import com.code.advancedsql.query.Create;
import com.code.advancedsql.table.ITable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import josscoder.economy.EconomyPlugin;
import org.bukkit.ChatColor;

public class MySQLProvider extends Provider {

  private MySQL mySQL;

  @Override
  public String getName() {
    return "MySQL";
  }

  public MySQLProvider init(
    String host,
    int port,
    String username,
    String password,
    String database
  ) {
    try {
      mySQL = new MySQL(host, port, username, password, database);

      if (mySQL.isConnected()) {
        initTable();
        EconomyPlugin
          .getInstance()
          .info(ChatColor.GREEN + "MySQL database connected!");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return this;
  }

  private void initTable() {
    try {
      ITable table = mySQL.table("users_in_the_bank");
      Create create = table.create().ifNotExists();

      create.id();
      create.longText("uniqueUid");
      create.integer("amount");

      create.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean contains(String uniqueUid) {
    try {
      return (
        mySQL
          .table("users_in_the_bank")
          .select()
          .where("uniqueUid = ?", uniqueUid)
          .fetch() !=
        null
      );
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return false;
  }

  @Override
  public void createAccount(String uniqueUid, int amount) {
    try {
      mySQL
        .table("users_in_the_bank")
        .insert()
        .fields(
          new HashMap<String, Object>() {
            {
              put("uniqueUid", uniqueUid);
              put("amount", amount);
            }
          }
        )
        .execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public int getUserCoins(String uniqueUid) {
    try {
      Map<String, Object> data = mySQL
          .table("users_in_the_bank")
          .select(new String[] { "amount" })
          .where("uniqueUid = ?", uniqueUid)
          .fetch();

      return (int) data.get("amount");
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return 0;
  }

  @Override
  public void set(String uniqueUid, int amount) {
    try {
      mySQL
        .table("users_in_the_bank")
        .update()
          .field("amount", amount)
          .where("uniqueUid = ?", uniqueUid)
        .execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void close() {
    try {
      mySQL.getConnection().close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
