package fr.ulity.core.api.functions;

import fr.ulity.core.api.Config;

import java.util.Date;
import java.util.HashMap;

public final class Ban {
    private static final Config banConf = new Config("ban");

    public static void ban(String username) {
        banConf.set(username, new HashMap<>());
    }

    public static void ban(String username, String reason) {
        HashMap<String, Object> truc = new HashMap<>();
        truc.put("reason", reason);

        banConf.set(username, truc);
    }

    public static void tempban(String username, int seconds) {
        HashMap<String, Object> truc = new HashMap<>();
        truc.put("expire", (Math.round(new Date().getTime() / 1000)) + seconds);

        banConf.set(username, truc);
    }

    public static void tempban(String username, int seconds, String reason) {
        HashMap<String, Object> truc = new HashMap<>();
        truc.put("expire", (Math.round(new Date().getTime() / 1000)) + seconds);
        truc.put("reason", reason);

        banConf.set(username, truc);
    }

    public static void unban(String username) {
        banConf.delete(username);
    }

    public static boolean isBanned(String username) {
        return banConf.isSet(username);
    }

}