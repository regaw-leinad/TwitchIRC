package com.danwager.irc.twitch.message.general;

import com.danwager.irc.twitch.message.ServerMessage;

public class MODEServerMessage extends ServerMessage {

    private String nick;
    private String channel;
    private boolean op;

    public MODEServerMessage() {}

    public MODEServerMessage(String nick, String channel, boolean op) {
        this.nick = nick;
        this.channel = channel;
        this.op = op;
    }

    public String getNick() {
        return this.nick;
    }

    public String getChannel() {
        return this.channel;
    }

    public boolean isOp() {
        return this.op;
    }

    @Override
    public String getId() {
        return "MODE";
    }

    @Override
    protected ServerMessage createFromServer(String[] rawSplit) {
        if (rawSplit.length != 4) {
            return null;
        }

        String channel = rawSplit[2];

        String[] split = rawSplit[3].split(" ");
        boolean op = split[0].equals("+o");
        String nick = split[1];

        return new MODEServerMessage(nick, channel, op);
    }
}
