package fr.ulity.core.api.bungee;

import fr.ulity.core.api.Api;
import fr.ulity.core.bungee.MainBungee;
import fr.ulity.core.utils.Version;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class InitializerBungee {
    public static ArrayList<Plugin> lesPlugins = new ArrayList<>();
    public static ArrayList<Class> lesClass = new ArrayList<>();

    public boolean ok = Api.ok;

    private final Plugin plugin;

    public InitializerBungee(@NotNull Plugin plugin) {
        this.plugin = plugin;
        if (!lesPlugins.contains(plugin)) {
            lesPlugins.add(plugin);
            lesClass.add(plugin.getClass());
        }
    }

    public boolean reloadLang () {
        try {
            LangBungee.reloadOneAddon(plugin);
            return true;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void removeClass (Plugin plugin) {
        lesPlugins.remove(plugin);
        lesClass.remove(plugin.getClass());
    }


    /* API USE */

    public void requireVersion (String version) {
        if (new Version(Api.version).compareTo(new Version(version)) < 0){
            MainBungee.plugin.getProxy().getScheduler().cancel(this.plugin);
            plugin.getLogger().severe("     §ethis plugin require minimum version §a" + version + " §eof UlityCore");
            plugin.getLogger().severe("§9█§f█§c█  §eCe plugin requiert la version minimum §a" + version + " §ede UlityCore");
            ok = false;
        }
    }

}
