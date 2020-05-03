package fr.ulity.moderation.bukkit;

import fr.ulity.core.api.Initializer;
import fr.ulity.core.api.Lang;
import fr.ulity.core.bukkit.MainBukkit;
import fr.ulity.moderation.bukkit.commands.ClearChatCommand;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public final class MainModBukkit extends JavaPlugin {
    public static MainModBukkit plugin;
    public static Server server;


    @Override
    public void onEnable() {
        plugin = this;
        server = getServer();

        Initializer.addClass(this.getClass());

        Lang.reloadOneAddon(MainModBukkit.class);

        // register commands :
        new ClearChatCommand(MainBukkit.commandMap, this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
