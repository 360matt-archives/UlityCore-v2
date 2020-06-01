package fr.ulity.core.api;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;

public class Api {
    public static boolean ok = true;
    public static String type;
    public static String version;

    public static Config config;
    public static Temp temp;
    public static Data data;

    public static String prefix;
    public static String full_prefix;

    public static void initialize(@NotNull Object plugin) {
        File folder = null;

        try {
            Class.forName("org.bukkit.plugin.Plugin");
            type = "bukkit";

            Bukkit.define();
            Bukkit.plugin = (org.bukkit.plugin.Plugin) plugin;
            version = Bukkit.plugin.getDescription().getVersion();
            folder = Bukkit.plugin.getDataFolder();

            /* try {
                Class.forName("com.destroystokyo.paper.VersionHistoryManager$VersionData");
            }
            catch (Exception ignored) {
                Bukkit.plugin.getLogger().severe("UlityCore require a Paper server. ( no Bukkit or Spigot )\n" +
                        "Because, Paper is 100000% more efficient than Spigot.");
                Bukkit.plugin.getPluginLoader().disablePlugin(Bukkit.plugin);
                ok = false;
            } */
        }
        catch (Exception ignored) {
            try {
                Class.forName("net.md_5.bungee.api.plugin.Plugin");
                type = "bungeecord";

                Bungee.define();
                Bungee.plugin = (net.md_5.bungee.api.plugin.Plugin) plugin;
                version = Bungee.plugin.getDescription().getVersion();
                folder = Bungee.plugin.getDataFolder();
            }
            catch (Exception ignored2) {
                System.out.println("§cWTF ! Bukkit/Spigot/Paper or BungeeCord/Waterfall are not detected. WtttFF");
                ok = false;
            }
        }

        if (ok){
            full_prefix = folder.getAbsolutePath();
            prefix = folder.toString().replaceAll("\\\\", "/");
            define();
        }

    }

    private static void define() {
        config = new Config();
        temp = new Temp();
        data = new Data();

        temp.clear();

        try {
            Lang.reload();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public static class Bukkit {
        public static org.bukkit.plugin.Plugin plugin;
        public static org.bukkit.command.CommandMap commandMap;

        public static void define () {
            try {
                Field f = org.bukkit.Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                commandMap = (org.bukkit.command.CommandMap) f.get(org.bukkit.Bukkit.getServer());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                ok = false;
            }
        }
    }

    public static class Bungee {
        public static net.md_5.bungee.api.plugin.Plugin plugin;

        public static void define () {

        }
    }


}
