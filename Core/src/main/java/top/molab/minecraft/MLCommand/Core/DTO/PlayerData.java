package top.molab.minecraft.MLCommand.Core.DTO;

import com.google.gson.Gson;

import java.util.UUID;

public class PlayerData {
    private String name;
    private UUID uuid;
    private int serviceID;
    private String serviceName;

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public String toString(){
        return toJson();
    }

    public static PlayerData fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, PlayerData.class);
    }

    public PlayerData(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
