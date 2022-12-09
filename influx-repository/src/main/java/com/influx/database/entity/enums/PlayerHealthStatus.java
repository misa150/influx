package com.influx.database.entity.enums;

public enum PlayerHealthStatus {

    ALIVE(1),
    DEAD(0);

    private int value;

    PlayerHealthStatus(int value) {
        this.value = value;
    }


    public int getPlayerHealthStatus() {
        return this.value;
    }

}
