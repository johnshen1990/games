package io.github.johnshen1990.games.drawsomething.utils;

import org.apache.commons.codec.binary.Base64;


/**
 * Author: zhun.shen
 * Date: 2017-05-27 17:07
 * Description:
 */
public class Base64Util {
    public static String convertBytes2Base64String(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    public static byte[] convertBase64String2Bytes(String base64String) throws Exception {
        return Base64.decodeBase64(base64String);
    }
}
