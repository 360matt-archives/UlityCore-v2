package fr.ulity.moderation.bukkit;

import fr.ulity.core.api.Config;
import fr.ulity.core.api.Initializer;
import fr.ulity.core.api.Lang;
import fr.ulity.core.bukkit.MainBukkit;
import fr.ulity.moderation.bukkit.commands.*;
import fr.ulity.moderation.bukkit.events.AntiInsultEvent;
import fr.ulity.moderation.bukkit.events.MuteEvent;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getPluginManager;

public final class MainModBukkit extends JavaPlugin {
    public static MainModBukkit plugin;
    public static Server server;


    @Override
    public void onEnable() {
        plugin = this;
        server = getServer();

        Initializer.addPlugin(this);
        Lang.reloadOneAddon(MainModBukkit.class);


        // register commands :
        new ClearChatCommand(MainBukkit.commandMap, this);
        new ChatCommand(MainBukkit.commandMap, this);
        new MuteCommand(MainBukkit.commandMap, this);
        new TempMuteCommand(MainBukkit.commandMap, this);
        new UnMuteCommand(MainBukkit.commandMap, this);

        // register/start modules :
        StartModule.start();


        // register events
        getPluginManager().registerEvents(new MuteEvent(), this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
