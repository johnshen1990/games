package io.github.johnshen1990.games.drawsomething.shared;

import io.github.johnshen1990.games.drawsomething.utils.AesUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: zhun.shen
 * Date: 2017-05-27 11:55
 * Description:
 */
public enum AesKeyInstance {
    INSTANCE;

    private ConcurrentHashMap<String, String> sessionId2AesKeyMap = new ConcurrentHashMap<>();

    public String get(String sessionId) {
        String aesKey = sessionId2AesKeyMap.get(sessionId);
        if(StringUtils.isBlank(aesKey)) {
            aesKey = AesUtil.generateRandomKey();
            sessionId2AesKeyMap.put(sessionId, aesKey);
        }
        return aesKey;
    }
}
