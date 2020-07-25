package fr.ulity.core.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Addons {
    private static boolean isAvailable (String name) { return Bukkit.getPluginManager().isPluginEnabled(name); }
    private static Plugin getInstance (String name) { return Bukkit.getPluginManager().getPlugin(name); }

    static class PackUtils {
        public static boolean available () { return isAvailable("PackUtils"); }
        public static Plugin getPlugin () { return getInstance("PackUtils"); }
    }
    static class Moderation {
        public static boolean available () { return isAvailable("Moderation"); }
        public static Plugin getPlugin () { return getInstance("Moderation"); }
    }
    static class DeluxeGUI {
        public static boolean available () { return isAvailable("DeluxeGUI"); }
        public static Plugin getPlugin () { return getInstance("DeluxeGUI"); }
    }
    static class SuperRTP {
        public static boolean available () { return isAvailable("SuperRTP"); }
        public static Plugin getPlugin () { return getInstance("SuperRTP"); }
    }
}
