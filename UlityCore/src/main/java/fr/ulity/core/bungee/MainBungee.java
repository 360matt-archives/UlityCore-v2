package fr.ulity.core.bungee;

import fr.ulity.core.api.Api;
import net.md_5.bungee.api.plugin.Plugin;


public class MainBungee extends Plugin {
    public static MainBungee plugin;

    @Override
    public void onEnable() {
        plugin = this;

        Api.initialize(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
