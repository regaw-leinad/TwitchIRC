package com.danwager.irc.twitch.message.jtv;

import com.danwager.irc.twitch.message.JTVMessage;
import com.danwager.irc.twitch.message.ServerMessage;

public class USERCOLORMessage extends JTVMessage {

    private String nick;
    private String color;

    public USERCOLORMessage() {
    }

    public USERCOLORMessage(String nick, String color) {
        this.nick = nick;
        this.color = color;
    }

    public String getNick() {
        return this.nick;
    }

    public String getColor() {
        return this.color;
    }

    @Override
    public String getId() {
        return "USERCOLOR";
    }

    @Override
    protected boolean handles(String message) {
        return message.startsWith(getId());
    }

    @Override
    protected ServerMessage createFromServer(String message) {
        String[] split = message.split(" ", 3);

        if (split.length != 3) {
            return null;
        }

        String nick = split[1];
        String color = split[2];

        return new USERCOLORMessage(nick, color);
    }
}
