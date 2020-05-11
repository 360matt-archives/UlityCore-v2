package fr.ulity.core.api;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.Field;

public class Api {
    public static boolean ok = true;

    public static Object pluginObj;

    public static String prefix;
    public static String full_prefix;
    public static String type;
    public static String version;

    public static Config config;
    public static Temp temp;
    public static Data data;



    public static void initialize(@NotNull Object plugin) {
        pluginObj = plugin;
        File folder = null;

        try {
            Class.forName("net.md_5.bungee.api.plugin.Plugin");
            type = "bungeecord";
            version = ((net.md_5.bungee.api.plugin.Plugin) plugin).getDescription().getVersion();
            folder = ((net.md_5.bungee.api.plugin.Plugin) plugin).getDataFolder();

            new Bungee();
        } catch (ClassNotFoundException ignored) {
            type = "bukkit";
            version = ((org.bukkit.plugin.Plugin) plugin).getDescription().getVersion();
            folder = ((org.bukkit.plugin.Plugin) plugin).getDataFolder();

            new Bukkit();
        }

        full_prefix = folder.getAbsolutePath();
        prefix = folder.toString().replaceAll("\\\\", "/");

        runny();
    }

    private static void runny() {
        config = new Config();
        temp = new Temp();
        data = new Data();

        temp.clear();
        Lang.reload();
    }


    public static class Bukkit {
        public static CommandMap commandMap;

        public Bukkit () {
            try {
                Field f = org.bukkit.Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                commandMap = (CommandMap) f.get(org.bukkit.Bukkit.getServer());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                ok = false;
            }
        }
    }

    public static class Bungee {

    }

}
