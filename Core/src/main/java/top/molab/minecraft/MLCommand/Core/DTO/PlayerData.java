package top.molab.minecraft.MLCommand.Core.DTO;

public class PlayerData {
  private PlayerLoginData playerLoginData;
  private PlayerServiceData playerServiceData;

  public PlayerLoginData getPlayerLoginData() {
    return playerLoginData;
  }

  public void setPlayerLoginData(PlayerLoginData playerLoginData) {
    this.playerLoginData = playerLoginData;
  }

  public PlayerServiceData getPlayerServiceData() {
    return playerServiceData;
  }

  public void setPlayerServiceData(PlayerServiceData playerServiceData) {
    this.playerServiceData = playerServiceData;
  }
}
