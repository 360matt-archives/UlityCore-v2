package fr.ulity.core.api;

import fr.ulity.core.bukkit.loaders.IsUpToDate;
import fr.ulity.core.utils.Version;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public final class Initializer {
    public static ArrayList<JavaPlugin> lesPlugins = new ArrayList<>();
    public static ArrayList<Class> lesClass = new ArrayList<>();

    public boolean ok = Api.ok;

    private final JavaPlugin plugin;

    public Initializer (@NotNull JavaPlugin plugin) {
        this.plugin = plugin;
        if (!lesPlugins.contains(plugin)) {
            lesPlugins.add(plugin);
            lesClass.add(plugin.getClass());
        }
    }

    public boolean reloadLang () {
        try {
            Lang.reloadOneAddon(plugin.getClass());
            return true;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void removeClass ( JavaPlugin plugin) {
        lesPlugins.remove(plugin);
        lesClass.remove(plugin.getClass());
    }


    /* API USE */

    public void requireVersion (String version) {
        if (new Version(Api.version).compareTo(new Version(version)) < 0){
            Bukkit.getPluginManager().disablePlugin(this.plugin);
            plugin.getLogger().severe("     §ethis plugin require minimum version §a" + version + " §eof UlityCore");
            plugin.getLogger().severe("§9█§f█§c█  §eCe plugin requiert la version minimum §a" + version + " §ede UlityCore");
            ok = false;
        }
    }

    public void checkUpdates (int SpigotMcResourceID) {
        new IsUpToDate(plugin, SpigotMcResourceID).noticeUpdate();
    }

}
