package com.danwager.irc.twitch.user;

import com.danwager.irc.twitch.TwitchIRC;
import com.danwager.irc.twitch.message.general.JOINServerMessage;
import com.danwager.irc.twitch.message.general.MODEServerMessage;
import com.danwager.irc.twitch.message.general.PRIVMSGServerMessage;
import com.danwager.irc.twitch.message.handler.HandlerPriority;
import com.danwager.irc.twitch.message.handler.ServerMessageAdapter;
import com.danwager.irc.twitch.message.jtv.USERCOLORMessage;

import java.util.HashMap;
import java.util.Map;

public class TwitchUserManager {

    private Map<String, TwitchUser> users;

    public TwitchUserManager(TwitchIRC client) {
        this.users = new HashMap<>();

        // Add handler that will run first
        client.registerServerMessageHandler(new ServerMessageAdapter() {

            @Override
            protected void handle(PRIVMSGServerMessage message) {
                ensureExists(message.getNick());
            }

            @Override
            protected void handle(JOINServerMessage message) {
                ensureExists(message.getNick());
            }

            @Override
            protected void handle(MODEServerMessage message) {
                boolean exists = exists(message.getNick());
                if (!exists && !message.isOp()) {
                    return;
                }

                TwitchUser user = ensureExists(message.getNick());
                user.setMod(message.isOp());
                // TODO: Event for mode change
            }

            @Override
            protected void handle(USERCOLORMessage message) {
                ensureExists(message.getNick()).setDisplayColor(message.getColor());
            }

            @Override
            public HandlerPriority getPriority() {
                return HandlerPriority.LOWEST;
            }
        });
    }

    private boolean exists(String nick) {
        return this.users.containsKey(nick);
    }

    private TwitchUser ensureExists(String nick) {
        if (!this.users.containsKey(nick)) {
            final TwitchUser newUser = new TwitchUser(nick);
            this.users.put(nick, newUser);
            new Thread(new DisplayNameFetcher(newUser)).start();
            /*Bukkit.getScheduler().runTaskAsynchronously(this.client.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    try {
                        newUser.setDisplayName(Util.getJSONObjectFromUrl("https://api.twitch.tv/kraken/channels/" + newUser.getNick()).getString("display_name"));
                    } catch (IOException e) {
                        System.err.println("Error grabbing display_name for " + newUser.getNick());
                    }
                }
            });
            Bukkit.getPluginManager().callEvent(new TwitchUserJoinEvent(newUser));*/
        }

        return this.users.get(nick);
    }

    public TwitchUser getUser(String nick) {
        return this.users.get(nick);
    }

    public void print() {
        System.out.println("TwitchUserManager");
        for (TwitchUser user : this.users.values()) {
            System.out.println(user);
        }
    }
}
