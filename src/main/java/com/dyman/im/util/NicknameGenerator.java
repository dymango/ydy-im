package com.dyman.im.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * @author dyman
 * @describe
 * @date 2020/2/5
 */
public class NicknameGenerator {

    private static final int NAME_LENGTH = 3;
    public static String get()
    {
        String randomName = "";
        for (int i = 0; i < NAME_LENGTH; i++) {
            String str = null;
            int hightPos, lowPos; // 定义高低位
            Random random = new Random();
            hightPos = (176 + Math.abs(random.nextInt(39))); // 获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93))); // 获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try {
                str = new String(b, "GBK"); // 转成中文
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            randomName += str;
        }

        return randomName;
    }
}
