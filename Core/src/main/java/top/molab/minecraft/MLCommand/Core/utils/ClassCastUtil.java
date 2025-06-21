package top.molab.minecraft.MLCommand.Core.utils;

import com.google.gson.internal.LinkedTreeMap;
import java.util.UUID;
import top.molab.minecraft.MLCommand.Core.DTO.PlayerData;
import top.molab.minecraft.MLCommand.Core.DTO.PlayerLoginData;
import top.molab.minecraft.MLCommand.Core.DTO.PlayerServiceData;

public class ClassCastUtil {
    public static PlayerServiceData getPlayerServiceFromLinkedTreeMap(LinkedTreeMap map){
        PlayerServiceData data = new PlayerServiceData();
        data.setName(map.get("name").toString());
        data.setUuid(UUID.fromString(map.get("uuid").toString()));
        data.setServiceID((int)Double.parseDouble(map.get("serviceID").toString()));
        data.setServiceName(map.get("serviceName").toString());
        return data;
    }

    public static PlayerLoginData getPlayerLoginDataFromLinkedTreeMap(LinkedTreeMap map){
        PlayerLoginData data = new PlayerLoginData();
        data.setName(map.get("name").toString());
        data.setUuid(UUID.fromString(map.get("uuid").toString()));
        data.setFirstLogin(Boolean.parseBoolean(map.get("isFirstLogin").toString()));
        return data;
    }

    public static PlayerData getPlayerDataFromLickedTreeMap(LinkedTreeMap map){
        LinkedTreeMap loginDataMap = (LinkedTreeMap) map.get("playerLoginData");
        LinkedTreeMap serviceDataMap = (LinkedTreeMap) map.get("playerServiceData");
        PlayerLoginData loginData = getPlayerLoginDataFromLinkedTreeMap(loginDataMap);
        PlayerServiceData serviceData = getPlayerServiceFromLinkedTreeMap(serviceDataMap);
        PlayerData data = new PlayerData();
        data.setPlayerLoginData(loginData);
        data.setPlayerServiceData(serviceData);
        return data;
    }

}
