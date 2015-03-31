package com.danwager.irc.twitch.message.general;

import com.danwager.irc.twitch.message.ServerMessage;

public class NoticeServerMessage extends ServerMessage {

    private String message;

    public NoticeServerMessage() {}

    public NoticeServerMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String getId() {
        return "NOTICE";
    }

    @Override
    protected ServerMessage createFromServer(String[] rawSplit) {
        if (rawSplit.length != 4) {
            return null;
        }

        return new NoticeServerMessage(rawSplit[3].substring(1).trim());
    }
}
