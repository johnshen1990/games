package io.github.johnshen1990.games.drawsomething.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Author: zhun.shen
 * Date: 2017-05-19 11:51
 * Description:
 */
public class UserEntity {
    @JSONField(name = "userId")
    private String userId;

    @JSONField(name = "userName")
    private String userName;

    public UserEntity() {}

    public UserEntity(String userName, String userId) {
        this.userName = userName;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
