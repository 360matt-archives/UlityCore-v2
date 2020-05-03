package fr.ulity.core.utils;

import fr.ulity.core.api.Api;

import java.util.Arrays;

public class Text {
    public static String full (String[] args) {
        StringBuilder returned = new StringBuilder();
        for (String x : args)
            returned.append(x).append(" ");
        return (returned.toString().trim());
    }

    public static String fullColor (String[] args) {
        return withColours(full(args));
    }

    public static String full (String[] args, int indice) {
        return full(Arrays.copyOfRange(args, 1, args.length));
    }

    public static String fullColor (String[] args, int indice) {
        return withColours(full(args, indice));
    }

    public static String withColours (String message) {
        if (Api.type.equals("bukkit"))
            return org.bukkit.ChatColor.translateAlternateColorCodes('&', message);
        else
            return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message);
    }

}
