package fr.ulity.moderation.bukkit;

import fr.ulity.core.api.Initializer;
import fr.ulity.core.api.Lang;
import org.bukkit.plugin.java.JavaPlugin;

public final class MainModBukkit extends JavaPlugin {
    public static MainModBukkit plugin;


    @Override
    public void onEnable() {
        plugin = this;
        Initializer.addClass(this.getClass());

        Lang.reloadOneAddon(MainModBukkit.class);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
