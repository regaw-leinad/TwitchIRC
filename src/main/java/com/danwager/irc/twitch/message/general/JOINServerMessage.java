package com.danwager.irc.twitch.message.general;

import com.danwager.irc.twitch.message.ServerMessage;

public class JOINServerMessage extends ServerMessage {

    private String nick;
    private String channel;

    public JOINServerMessage() {}

    public JOINServerMessage(String nick, String channel) {
        this.nick = nick;
        this.channel = channel;
    }

    public String getNick() {
        return this.nick;
    }

    public String getChannel() {
        return this.channel;
    }

    @Override
    public String getId() {
        return "JOIN";
    }

    @Override
    protected ServerMessage createFromServer(String[] rawSplit) {
        if (rawSplit.length != 3) {
            return null;
        }

        String nick = rawSplit[0];
        // Remove leading ':' and go until '!'
        nick = nick.substring(1, nick.indexOf("!"));

        String channel = rawSplit[2];

        return new JOINServerMessage(nick, channel);
    }
}
