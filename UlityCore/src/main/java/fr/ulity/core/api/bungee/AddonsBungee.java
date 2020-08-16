package fr.ulity.core.api.bungee;


import fr.ulity.core.bungee.MainBungee;
import net.md_5.bungee.api.plugin.Plugin;

public class AddonsBungee {
    private static boolean isAvailable (String name) { return MainBungee.plugin.getProxy().getPluginManager().getPlugin(name) != null; }
    private static Plugin getInstance (String name) { return MainBungee.plugin.getProxy().getPluginManager().getPlugin(name); }


}
