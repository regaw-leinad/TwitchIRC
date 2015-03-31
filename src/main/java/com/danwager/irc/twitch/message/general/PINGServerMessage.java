package com.danwager.irc.twitch.message.general;

import com.danwager.irc.twitch.message.ServerMessage;

public class PINGServerMessage extends ServerMessage {

    private String data;

    public PINGServerMessage() {}

    public PINGServerMessage(String data) {
        this.data = data;
    }

    public String getData() {
        return this.data;
    }

    @Override
    public String getId() {
        return "PING";
    }

    @Override
    protected ServerMessage createFromServer(String[] rawSplit) {
        if (rawSplit.length != 2) {
            return null;
        }

        return new PINGServerMessage(rawSplit[1]);
    }
}
