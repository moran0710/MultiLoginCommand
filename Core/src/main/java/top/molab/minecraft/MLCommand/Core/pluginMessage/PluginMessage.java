package top.molab.minecraft.MLCommand.Core.pluginMessage;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;



public class PluginMessage {
    private MessageTypes messageType;
    private Object data;

    public MessageTypes getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageTypes messageType) {
        this.messageType = messageType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public byte[] toBytes(){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(this.toJson());
        return  out.toByteArray();
    }

    public static PluginMessage fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, PluginMessage.class);
    }
}
