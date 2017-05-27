package io.github.johnshen1990.games.drawsomething.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Author: zhun.shen
 * Date: 2017-05-19 11:49
 * Description:
 */
public class RoomEntity {
    @JSONField(name = "roomId")
    private String roomId = UUID.randomUUID().toString();

    @JSONField(name = "userList")
    private List<UserEntity> userEntityList = new ArrayList<>();

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public List<UserEntity> getUserEntityList() {
        return userEntityList;
    }

    public void setUserEntityList(List<UserEntity> userEntityList) {
        this.userEntityList = userEntityList;
    }
}
