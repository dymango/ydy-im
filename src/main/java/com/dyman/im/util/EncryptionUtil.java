package com.dyman.im.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Description 加解密工具
 **/
public class EncryptionUtil {

    /**
     * MD5加密
     * @param value
     * @return
     */
    public static String md5Encryption(String value) {
        String encodeStr= DigestUtils.md5Hex((value));
        return encodeStr;
    }
}
