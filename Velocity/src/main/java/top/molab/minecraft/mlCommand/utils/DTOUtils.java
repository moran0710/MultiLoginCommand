package top.molab.minecraft.mlCommand.utils;

import moe.caa.multilogin.api.data.MultiLoginPlayerData;
import top.molab.minecraft.MLCommand.Core.DTO.PlayerData;

public class DTOUtils {
    public static PlayerData getPlayerDataFromMultiLoginData(MultiLoginPlayerData data){
        PlayerData playerData = new PlayerData();
        playerData.setName(data.getOnlineProfile().getName());
        playerData.setUuid(data.getOnlineProfile().getId());
        playerData.setServiceID(data.getLoginService().getServiceId());
        playerData.setServiceName(data.getLoginService().getServiceName());
        return playerData;
    }
}
