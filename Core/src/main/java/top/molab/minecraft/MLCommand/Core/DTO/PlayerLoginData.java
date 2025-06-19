package top.molab.minecraft.MLCommand.Core.DTO;

import java.util.UUID;

public class PlayerLoginData {
  private String name;
  private UUID uuid;
  private boolean isFirstLogin;

  public boolean isFirstLogin() {
    return isFirstLogin;
  }

  public void setFirstLogin(boolean firstLogin) {
    isFirstLogin = firstLogin;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
