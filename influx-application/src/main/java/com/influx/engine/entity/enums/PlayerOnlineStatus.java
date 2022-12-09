package com.influx.engine.entity.enums;

public enum PlayerOnlineStatus {

    ONLINE(1),
    OFFLINE(0);

    private int value;

    PlayerOnlineStatus(int value) {
        this.value = value;
    }


    public int getPlayerOnlineStatus() {
        return this.value;
    }
}
