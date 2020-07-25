package fr.ulity.deluxegui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class TranslateVars {
    public static HashMap<String, String> pass (Player player, HashMap<String, String> vars) {
        vars.put("player", player.getName());
        vars.put("world", player.getWorld().getName());
        vars.put("online", String.valueOf(Bukkit.getOnlinePlayers().size()));
        return vars;
    }

    public static String forText (String str, HashMap<String, String> vars) {
        for (Map.Entry<String, String> x : vars.entrySet())
            str = str.replaceAll("%" + x.getKey() + "%", x.getValue());
        return str;
    }

}