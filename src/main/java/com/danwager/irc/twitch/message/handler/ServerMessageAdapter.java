package com.danwager.irc.twitch.message.handler;

import com.danwager.irc.twitch.message.general.JOINServerMessage;
import com.danwager.irc.twitch.message.general.MODEServerMessage;
import com.danwager.irc.twitch.message.general.NoticeServerMessage;
import com.danwager.irc.twitch.message.general.PARTServerMessage;
import com.danwager.irc.twitch.message.general.PINGServerMessage;
import com.danwager.irc.twitch.message.general.PRIVMSGServerMessage;
import com.danwager.irc.twitch.message.jtv.USERCOLORMessage;
import com.danwager.irc.twitch.message.general.WelcomeServerMessage;

public abstract class ServerMessageAdapter extends ServerMessageHandler {

    protected void handle(JOINServerMessage message) {
    }

    protected void handle(MODEServerMessage message) {
    }

    protected void handle(NoticeServerMessage message) {
    }

    protected void handle(PARTServerMessage message) {
    }

    protected void handle(PINGServerMessage message) {
    }

    protected void handle(PRIVMSGServerMessage message) {
    }

    protected void handle(USERCOLORMessage message) {
    }

    protected void handle(WelcomeServerMessage message) {
    }
}
