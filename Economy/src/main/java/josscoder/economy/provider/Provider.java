package josscoder.economy.provider;

public abstract class Provider implements IProvider {

  @Override
  public void createAccount(String uniqueUid) {
    createAccount(uniqueUid, 0);
  }
}
