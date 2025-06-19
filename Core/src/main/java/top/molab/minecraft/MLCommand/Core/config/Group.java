package top.molab.minecraft.MLCommand.Core.config;

public class Group {
    private int serviceID;
    private String groupName;
    private String[] commands;
    private String[] firstJoinCommands;

    public Group(){}

    public Group(int serviceID, String groupName, String[] commands, String[] firstJoinCommands) {
        this.serviceID = serviceID;
        this.groupName = groupName;
        this.firstJoinCommands = firstJoinCommands;
        this.commands = commands;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String[] getCommands() {
        return commands;
    }

    public void setCommands(String[] commands) {
        this.commands = commands;
    }

    public String[] getFirstJoinCommands() {
        return firstJoinCommands;
    }

    public void setFirstJoinCommands(String[] firstJoinCommands) {
        this.firstJoinCommands = firstJoinCommands;
    }
}
