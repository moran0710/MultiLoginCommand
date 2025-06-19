package top.molab.minecraft.MLCommand.Core.utils;

import java.util.Random;

public class StringUtils {
  public static String getRandomEchoString() {
    return System.currentTimeMillis() + String.valueOf(new Random().nextInt(0, 999));
  }
}
