package com.danwager.irc.twitch.message.handler;

public enum HandlerPriority {

    LOWEST((byte)-64),
    LOW((byte)-32),
    NORMAL((byte)0),
    HIGH((byte)32),
    HIGHEST((byte)64);

    private final byte value;

    HandlerPriority(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return this.value;
    }
}
