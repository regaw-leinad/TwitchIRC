package com.danwager.irc.twitch.config;

import java.io.File;

public class ConnectionConfig {

    private String nick;
    private String password;
    private String channel;
    private File logLocation;

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

    public File getLogLocation() {
        return this.logLocation;
    }

    public void setLogLocation(String logLocation) {
        this.logLocation = new File(logLocation);
    }
}
