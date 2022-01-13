package josscoder.economy.provider;

public interface IProvider {
  String getName();
  boolean contains(String uniqueUid);
  void createAccount(String uniqueUid, int amount);
  void createAccount(String uniqueUid);
  int getUserCoins(String uniqueUid);
  void set(String uniqueUid, int amount);
  void close();
}
