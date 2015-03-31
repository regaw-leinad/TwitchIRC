package com.danwager.irc.twitch;

import com.danwager.irc.twitch.config.ConnectionConfig;
import com.danwager.irc.twitch.log.IRCLogFormatter;
import com.danwager.irc.twitch.message.ServerMessage;
import com.danwager.irc.twitch.message.ServerMessageFactory;
import com.danwager.irc.twitch.message.general.NoticeServerMessage;
import com.danwager.irc.twitch.message.general.PINGServerMessage;
import com.danwager.irc.twitch.message.general.WelcomeServerMessage;
import com.danwager.irc.twitch.message.handler.HandlerPriority;
import com.danwager.irc.twitch.message.handler.ServerMessageAdapter;
import com.danwager.irc.twitch.message.handler.ServerMessageHandler;
import com.danwager.irc.twitch.user.TwitchUserManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class TwitchIRC {

    public static final String TWITCH_IRC_HOST = "irc.twitch.tv";
    public static final int TWITCH_IRC_PORT = 6667;

    private static final Logger LOGGER = Logger.getLogger("TwitchIRC");

    static {
        try {
            FileHandler fh = new FileHandler("TwitchIRC-" + System.currentTimeMillis() + ".log");
            fh.setFormatter(new IRCLogFormatter());
            /*ConsoleHandler ch = new ConsoleHandler();
            ch.setFormatter(new IRCLogFormatter());*/
            LOGGER.addHandler(fh);
            /*LOGGER.addHandler(ch);*/
            LOGGER.setUseParentHandlers(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String PONG = "PONG";
    public static final String PASS = "PASS";
    public static final String NICK = "NICK";
    public static final String JOIN = "JOIN";
    public static final String PART = "PART";
    public static final String PRIVMSG = "PRIVMSG";

    private final ConnectionConfig config;
    private final ServerMessageFactory serverMessageFactory;
    private final Set<ServerMessageHandler> serverMessageHandlers;
    private TwitchUserManager userManager;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    private boolean started;
    private boolean connected;

    public TwitchIRC(final ConnectionConfig config) {
        this.config = config;

        this.serverMessageFactory = new ServerMessageFactory();
        this.serverMessageHandlers = new TreeSet<>(new Comparator<ServerMessageHandler>() {
            @Override
            public int compare(ServerMessageHandler h1, ServerMessageHandler h2) {
                return Byte.compare(h1.getPriority().getValue(), h2.getPriority().getValue());
            }
        });
        this.userManager = new TwitchUserManager(this);

        this.started = false;
        this.connected = false;

        this.serverMessageHandlers.add(new ServerMessageAdapter() {
            @Override
            protected void handle(NoticeServerMessage message) {
                if (message.getMessage().equals("Login unsuccessful")) {
                    System.err.println("Invalid login credentials!");
                    System.err.println("Wrong username or password?");
                    stop();
                }
            }

            @Override
            protected void handle(PINGServerMessage message) {
                sendRaw(PONG, message.getData());
            }

            @Override
            protected void handle(WelcomeServerMessage message) {
                System.out.println("Login successful!");
                sendRaw("TWITCHCLIENT 3");
                sendRaw(JOIN, config.getChannel());
            }

            @Override
            public HandlerPriority getPriority() {
                return HandlerPriority.NORMAL;
            }
        });
    }

    public boolean isStarted() {
        return this.started;
    }

    public void start() {
        if (!this.started) {
            System.out.println("Starting IRC Client");
            try {
                connect();
                login();
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                return;
            }

            Thread ioThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean loop = true;

                    while (loop && !Thread.currentThread().isInterrupted()) {
                        try {
                            String raw = reader.readLine();

                            getLogger().info(raw);

                            if (raw != null && !raw.isEmpty()) {
                                ServerMessage message = getServerMessageFactory().create(raw);
                                if (message != null) {
                                    handleServerMessage(message);
                                }
                            }
                        } catch (IOException ex) {
                            loop = false;
                        }
                    }

                    disconnect();
                }
            });
            ioThread.setName("TwitchIRCClient-io");
            ioThread.start();
            this.started = true;
        }
    }

    public void stop() {
        if (this.started) {
            System.out.println("Stopping IRC Client");
            forceDisconnect();
            this.started = false;
        }
    }

    private void connect() throws IOException {
        if (!this.connected) {
            System.out.println("Connecting to Twitch");

            this.socket = new Socket(TWITCH_IRC_HOST, TWITCH_IRC_PORT);
            this.writer = new PrintWriter(this.socket.getOutputStream(), true);
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            this.connected = true;

            System.out.println("Connected to Twitch");
        }
    }

    private void login() {
        System.out.println("Logging in");

        sendRaw(PASS, this.config.getPassword());
        sendRaw(NICK, this.config.getNick());
    }

    private void forceDisconnect() {
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disconnect() {
        try {
            sendRaw(PART);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                this.writer.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            } finally {
                this.writer = null;
                try {
                    this.reader.close();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                } finally {
                    this.reader = null;
                    try {
                        this.socket.close();
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    } finally {
                        this.socket = null;
                        this.connected = false;
                    }
                }
            }
        }
    }

    public void sendRaw(String data) {
        this.writer.print(data.trim());
        this.writer.print("\r\n");
        this.writer.flush();
    }

    public void sendRaw(String command, String data) {
        sendRaw(command.trim() + " " + data.trim());
    }

    public void sendMessage(String message) {
        sendRaw(PRIVMSG, this.config.getChannel() + " :" + message);
    }

    private ServerMessageFactory getServerMessageFactory() {
        return this.serverMessageFactory;
    }

    private void handleServerMessage(ServerMessage message) {
        for (ServerMessageHandler handler : this.serverMessageHandlers) {
            handler.handle(message);
        }
    }

    public final void registerServerMessageHandler(ServerMessageHandler handler) {
        this.serverMessageHandlers.add(handler);
    }

    public Logger getLogger() {
        return LOGGER;
    }

    public TwitchUserManager getUserManager() {
        return this.userManager;
    }
}
