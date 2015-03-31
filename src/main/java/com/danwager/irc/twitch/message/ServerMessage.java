package com.danwager.irc.twitch.message;

public abstract class ServerMessage {

    protected abstract String getId();

    protected abstract ServerMessage createFromServer(String[] rawSplit);
}
