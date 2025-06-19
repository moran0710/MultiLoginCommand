package top.molab.minecraft.mlCommand.utils;

import moe.caa.multilogin.api.data.MultiLoginPlayerData;
import top.molab.minecraft.MLCommand.Core.DTO.PlayerServiceData;

public class DTOUtils {
  public static PlayerServiceData getPlayerDataFromMultiLoginData(MultiLoginPlayerData data) {
    PlayerServiceData playerServiceData = new PlayerServiceData();
    playerServiceData.setName(data.getOnlineProfile().getName());
    playerServiceData.setUuid(data.getOnlineProfile().getId());
    playerServiceData.setServiceID(data.getLoginService().getServiceId());
    playerServiceData.setServiceName(data.getLoginService().getServiceName());
    return playerServiceData;
  }
}
