package io.github.johnshen1990.games.drawsomething.message;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Author: zhun.shen
 * Date: 2017-05-19 11:57
 * Description:
 */
public class BaseMessage {
    @JSONField(name = "messageType")
    protected MessageTypeEnum messageType = MessageTypeEnum.UNDIFINED;

    public MessageTypeEnum getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageTypeEnum messageType) {
        this.messageType = messageType;
    }
}
