package com.danwager.irc.twitch.config;

public class ConnectionConfig {

    private String nick;
    private String password;
    private String channel;

    public ConnectionConfig() {
        this("", "", "");
    }

    public ConnectionConfig(String nick, String password, String channel) {
        this.nick = nick;
        this.password = password;
        this.channel = channel;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel.startsWith("#") ? channel : "#" + channel;
    }
}
