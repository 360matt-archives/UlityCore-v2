package fr.ulity.superjails.bukkit;

import fr.ulity.core.api.Api;
import fr.ulity.core.api.Initializer;
import fr.ulity.superjails.bukkit.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class MainJailsBukkit extends JavaPlugin {
    public static MainJailsBukkit plugin;

    @Override
    public void onEnable() {
        plugin = this;

        Initializer init = new Initializer(this);
        init.requireVersion("2.1.1");
        init.reloadLang();

        if (init.ok) {


            // la suite du code de l'Addons
            new JailsAdminCommand(Api.Bukkit.commandMap, this);
            new JailCommand(Api.Bukkit.commandMap, this);

        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
