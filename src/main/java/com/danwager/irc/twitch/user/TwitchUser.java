package com.danwager.irc.twitch.user;

public class TwitchUser {

    private String nick;
    private String displayName;
    private boolean mod;
    private String displayColor;

    public TwitchUser(String nick) {
        this(nick, false);
        this.displayName = nick;
    }

    public TwitchUser(String nick, boolean mod) {
        this.nick = nick;
        this.mod = mod;
    }

    public String getNick() {
        return nick;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayColor() {
        return displayColor;
    }

    public void setDisplayColor(String displayColor) {
        this.displayColor = displayColor;
    }

    public boolean isMod() {
        return mod;
    }

    public void setMod(boolean mod) {
        this.mod = mod;
    }

    @Override
    public String toString() {
        return "TwitchUser{" +
                "nick=" + getNick() + ", " +
                "displayName=" + getDisplayName() + ", " +
                "displayColor=" + getDisplayColor() + ", " +
                "isMod=" + isMod() + "}";
    }
}
