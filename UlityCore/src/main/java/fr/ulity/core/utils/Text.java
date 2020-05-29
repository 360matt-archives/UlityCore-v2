package fr.ulity.core.utils;

import fr.ulity.core.api.Api;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
        return full(Arrays.copyOfRange(args, indice, args.length));
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

    public static String convertEncodage (String str) {
        return new String(str.getBytes(), StandardCharsets.UTF_8);
    }

    public static String[] convertEncodage (String[] arr) {
        ArrayList<String> newArr = new ArrayList<String>();

        for (String x : arr)
            newArr.add(convertEncodage(x));

        return newArr.toArray(new String[0]);
    }

    public String centerText(String text) {
        int maxWidth = 80,
                spaces = (int) Math.round((maxWidth-1.4* ChatColor.stripColor(text).length())/2);
        return StringUtils.repeat(" ", spaces)+text;
    }


}
