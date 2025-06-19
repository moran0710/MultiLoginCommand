package top.molab.minecraft.MLCommand.Core.pluginMessage;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;

public class PluginMessage {
  private MessageTypes messageType;
  private Object data;
  private String echo;

  public static PluginMessage fromBytes(byte[] bytes) {
    ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
    return PluginMessage.fromJson(in.readUTF());
  }

  public static PluginMessage fromJson(String json) {
    Gson gson = new Gson();
    return gson.fromJson(json, PluginMessage.class);
  }

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

  public String toJson() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

  public byte[] toBytes() {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF(this.toJson());
    return out.toByteArray();
  }

  public String getEcho() {
    return echo;
  }

  public void setEcho(String echo) {
    this.echo = echo;
  }
}
