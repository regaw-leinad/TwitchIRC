package com.danwager.irc.twitch.user;

import com.danwager.irc.twitch.util.Util;

import java.io.IOException;

public class DisplayNameFetcher implements Runnable {

    private final TwitchUser user;

    public DisplayNameFetcher(TwitchUser user) {
        this.user = user;
    }

    @Override
    public void run() {
        if (!this.user.getNick().equalsIgnoreCase("jtv")) {
            try {
                this.user.setDisplayName(Util.getJSONObjectFromUrl("https://api.twitch.tv/kraken/channels/" + this.user.getNick()).getString("display_name"));
            } catch (IOException e) {
                System.err.println("Error grabbing display_name for " + this.user.getNick());
                e.printStackTrace();
            }
        }
    }
}
