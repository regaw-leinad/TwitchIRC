package com.danwager.irc.twitch.message.general;

import com.danwager.irc.twitch.message.ServerMessage;

public class PRIVMSGServerMessage extends ServerMessage {

    private String nick;
    private String channel;
    private String message;

    public PRIVMSGServerMessage() {
    }

    public PRIVMSGServerMessage(String nick, String channel, String message) {
        this.nick = nick;
        this.channel = channel;
        this.message = message;
    }

    public String getNick() {
        return this.nick;
    }

    public String getChannel() {
        return this.channel;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String getId() {
        return "PRIVMSG";
    }

    @Override
    protected ServerMessage createFromServer(String[] rawSplit) {
        if (rawSplit.length != 4) {
            return null;
        }

        String nick = rawSplit[0];
        // Remove leading ':' and go until '!'
        nick = nick.substring(1, nick.indexOf("!"));

        String channel = rawSplit[2];

        String message = rawSplit[3];
        if (!message.isEmpty()) {
            // Remove leading ':'
            message = message.substring(1);
        }

        return new PRIVMSGServerMessage(nick, channel, message);
    }
}
