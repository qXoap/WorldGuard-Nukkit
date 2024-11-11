package dev.xoapp.worldguard.session;

import cn.nukkit.Player;

import java.util.HashMap;
import java.util.Map;

public class SessionFactory {

    private static final Map<String, Session> sessions = new HashMap<>();

    public static void register(Player player) {
        sessions.put(player.getName(), new Session(player.getName()));
    }

    public static Session get(String name) {
        return sessions.get(name);
    }

    public static void delete(String name) {
        sessions.remove(name);
    }

    public static void close() {
        sessions.forEach((name, session) -> {
            if (session.getProcess() == null) {
                return;
            }

            session.returnInventory();
        });
    }
}