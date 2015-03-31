package com.danwager.irc.twitch.message;

import com.danwager.irc.twitch.message.general.JOINServerMessage;
import com.danwager.irc.twitch.message.general.MODEServerMessage;
import com.danwager.irc.twitch.message.general.NoticeServerMessage;
import com.danwager.irc.twitch.message.general.PARTServerMessage;
import com.danwager.irc.twitch.message.general.PINGServerMessage;
import com.danwager.irc.twitch.message.general.PRIVMSGServerMessage;
import com.danwager.irc.twitch.message.general.WelcomeServerMessage;
import com.danwager.irc.twitch.message.jtv.USERCOLORMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerMessageFactory {

    private Map<String, ServerMessage> messages;
    private List<JTVMessage> jtvMessages;

    public ServerMessageFactory() {
        this.messages = new HashMap<>();
        this.jtvMessages = new ArrayList<>();

        registerServerMessage(new JOINServerMessage());
        registerServerMessage(new MODEServerMessage());
        registerServerMessage(new NoticeServerMessage());
        registerServerMessage(new PARTServerMessage());
        registerServerMessage(new PINGServerMessage());
        registerServerMessage(new PRIVMSGServerMessage());
        registerServerMessage(new WelcomeServerMessage());

        registerJtvMessage(new USERCOLORMessage());
    }

    public ServerMessage create(String raw) {
        if (raw == null || raw.isEmpty()) {
            return null;
        }

        String[] split = raw.split(" ", 4);

        ServerMessage dummy = getServerMessage(split[0]);
        if (dummy != null) {
            return dummy.createFromServer(split);
        }

        if (split.length > 1) {
            dummy = getServerMessage(split[1]);
            if (dummy != null) {
                ServerMessage result = dummy.createFromServer(split);

                // -_- have to hardcode for now to
                // handle 'TWITCHCLIENT 3' messages
                if (result instanceof PRIVMSGServerMessage) {
                    PRIVMSGServerMessage message = (PRIVMSGServerMessage)result;

                    if (message.getNick().equalsIgnoreCase("jtv")) {
                        for (JTVMessage m : this.jtvMessages) {
                            if (m.handles(message.getMessage())) {
                                result = m.createFromServer(message.getMessage());
                                break;
                            }
                        }
                    }
                }

                return result;
            }
        }

        return null;
    }

    private ServerMessage getServerMessage(String name) {
        return this.messages.get(name);
    }

    private void registerServerMessage(ServerMessage message) {
        this.messages.put(message.getId(), message);
    }

    private void registerJtvMessage(JTVMessage message) {
        this.jtvMessages.add(message);
    }
}
