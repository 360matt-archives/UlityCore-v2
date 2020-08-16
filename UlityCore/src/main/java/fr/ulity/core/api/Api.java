package fr.ulity.core.api;

import fr.ulity.core.api.bukkit.LangBukkit;
import fr.ulity.core.api.bungee.LangBungee;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class Api {
    public static PluginType type;
    public static String version;
    public static String corePath;

    public static boolean ok = true;

    public static Config config;
    public static Temp temp;
    public static Data data;

    public static enum PluginType {
        BUKKIT, BUNGEE
    }

    public static void initialize(@NotNull org.bukkit.plugin.java.JavaPlugin plugin) {
        try {
            type = PluginType.BUKKIT;
            version = plugin.getDescription().getVersion();
            corePath = plugin.getDataFolder().getAbsolutePath().replaceAll("\\\\", "/");

            config = new Config();
            data = new Data();
            temp = new Temp();
            temp.clear();

            LangBukkit.reload();
        } catch (Exception e) {
            e.printStackTrace();
            ok = false;
        }
    }

    public static void initialize(@NotNull net.md_5.bungee.api.plugin.Plugin plugin) {
        try {
            type = PluginType.BUNGEE;
            version = plugin.getDescription().getVersion();
            corePath = plugin.getDataFolder().getAbsolutePath().replaceAll("\\\\", "/");

            config = new Config();
            data = new Data();
            temp = new Temp();
            temp.clear();

            LangBungee.reload();
        } catch (Exception e) {
            e.printStackTrace();
            ok = false;
        }
    }

    public static org.bukkit.command.CommandMap getCommandMap () {
        try {
            Field f = org.bukkit.Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            return (org.bukkit.command.CommandMap) f.get(org.bukkit.Bukkit.getServer());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            ok = false;
            return null;
        }
    }

}
