package io.github.johnshen1990.games.drawsomething.message;

import com.alibaba.fastjson.annotation.JSONField;
import io.github.johnshen1990.games.drawsomething.entity.RoomEntity;

/**
 * Author: zhun.shen
 * Date: 2017-05-19 11:56
 * Description:
 */
public class RoomInfoMessage extends BaseMessage {
    @JSONField(name = "roomInfo")
    private RoomEntity roomEntity = new RoomEntity();

    public RoomInfoMessage(RoomEntity roomEntity) {
        super();
        super.messageType = MessageTypeEnum.ROOM_INFO;
        this.roomEntity = roomEntity;
    }

    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    public void setRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
    }
}
