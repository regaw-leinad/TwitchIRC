package com.danwager.irc.twitch.message.general;

import com.danwager.irc.twitch.message.ServerMessage;

public class WelcomeServerMessage extends ServerMessage {

    public WelcomeServerMessage() {}

    @Override
    public String getId() {
        return "001";
    }

    @Override
    protected ServerMessage createFromServer(String[] rawSplit) {
        return new WelcomeServerMessage();
    }
}
