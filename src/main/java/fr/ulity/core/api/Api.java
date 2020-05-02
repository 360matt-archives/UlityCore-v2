package fr.ulity.core.api;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Api {
    public static String prefix;
    public static String full_prefix;
    public static String type;
    public static String name;

    public static Config config;
    public static Temp temp;
    public static Data data;

    public static Object pluginObj;

    public static void initialize(@NotNull net.md_5.bungee.api.plugin.Plugin plugin) {
        pluginObj = plugin;
        File folder = ((net.md_5.bungee.api.plugin.Plugin) pluginObj).getDataFolder();
        folder.mkdir();

        type = "bungeecord";
        name = ((net.md_5.bungee.api.plugin.Plugin) pluginObj).getDescription().getName();
        full_prefix = folder.getAbsolutePath();
        prefix = folder.toString().replaceAll("\\\\", "/");

        runny();
    }

    public static void initialize(@NotNull org.bukkit.plugin.Plugin plugin) {
        pluginObj = plugin;
        File folder = ((org.bukkit.plugin.Plugin) pluginObj).getDataFolder();
        folder.mkdir();

        type = "bukkit";
        name = ((org.bukkit.plugin.Plugin) pluginObj).getName();
        prefix = folder.getPath().replaceAll("\\\\", "/");

        runny();
    }

    private static void runny() {
        config = new Config();
        temp = new Temp();
        data = new Data();

        Lang.reload();
    }
}
