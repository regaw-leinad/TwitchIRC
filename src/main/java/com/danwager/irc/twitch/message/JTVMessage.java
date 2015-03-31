package com.danwager.irc.twitch.message;

import org.apache.commons.lang3.StringUtils;

public abstract class JTVMessage extends ServerMessage {

    protected abstract boolean handles(String message);

    protected abstract ServerMessage createFromServer(String message);

    @Override
    protected final ServerMessage createFromServer(String[] rawSplit) {
        return createFromServer(StringUtils.join(rawSplit, ' '));
    }
}
