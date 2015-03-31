package com.danwager.irc.twitch.message.handler;

import com.danwager.irc.twitch.message.general.JOINServerMessage;
import com.danwager.irc.twitch.message.general.MODEServerMessage;
import com.danwager.irc.twitch.message.general.NoticeServerMessage;
import com.danwager.irc.twitch.message.general.PARTServerMessage;
import com.danwager.irc.twitch.message.general.PINGServerMessage;
import com.danwager.irc.twitch.message.general.PRIVMSGServerMessage;
import com.danwager.irc.twitch.message.ServerMessage;
import com.danwager.irc.twitch.message.jtv.USERCOLORMessage;
import com.danwager.irc.twitch.message.general.WelcomeServerMessage;

public abstract class ServerMessageHandler {

    public final void handle(ServerMessage message) {
        if (message instanceof JOINServerMessage) {
            handle((JOINServerMessage)message);
        } else if (message instanceof MODEServerMessage) {
            handle((MODEServerMessage)message);
        } else if (message instanceof NoticeServerMessage) {
            handle((NoticeServerMessage)message);
        } else if (message instanceof PARTServerMessage) {
            handle((PARTServerMessage)message);
        } else if (message instanceof PINGServerMessage) {
            handle((PINGServerMessage)message);
        } else if (message instanceof PRIVMSGServerMessage) {
            handle((PRIVMSGServerMessage)message);
        } else if (message instanceof USERCOLORMessage) {
            handle((USERCOLORMessage)message);
        } else if (message instanceof WelcomeServerMessage) {
            handle((WelcomeServerMessage)message);
        }
    }

    protected abstract void handle(JOINServerMessage message);

    protected abstract void handle(MODEServerMessage message);

    protected abstract void handle(NoticeServerMessage message);

    protected abstract void handle(PARTServerMessage message);

    protected abstract void handle(PINGServerMessage message);

    protected abstract void handle(PRIVMSGServerMessage message);

    protected abstract void handle(USERCOLORMessage message);

    protected abstract void handle(WelcomeServerMessage message);

    public abstract HandlerPriority getPriority();
}
