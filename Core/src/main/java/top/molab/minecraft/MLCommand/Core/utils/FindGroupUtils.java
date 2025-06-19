package top.molab.minecraft.MLCommand.Core.utils;

import top.molab.minecraft.MLCommand.Core.config.Group;

public class FindGroupUtils {
    public static Group findGroup(Group[] groups, int serviceID){
        for (Group group : groups) {
            if (group.getServiceID() == serviceID){
                return group;
            }
        }
        return null;
    }
}
