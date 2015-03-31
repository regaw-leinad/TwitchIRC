package com.danwager.irc.twitch;

import com.danwager.irc.twitch.config.ConnectionConfig;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ConnectionConfig config = new ConnectionConfig();
        config.setNick("EximiusBot");
        config.setPassword("oauth:");
        config.setChannel("#regaw_leinad");

        TwitchIRC irc = new TwitchIRC(config);
        irc.start();

        Scanner scanner = new Scanner(System.in);

        while (!Thread.interrupted()) {
            scanner.nextLine();
            irc.getUserManager().print();
            System.out.println();
        }
    }
}