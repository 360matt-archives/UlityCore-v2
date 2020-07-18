package fr.ulity.moderation.bukkit;


import fr.ulity.core.api.*;
import fr.ulity.moderation.bukkit.commands.*;
import fr.ulity.moderation.bukkit.events.BanEvent;
import fr.ulity.moderation.bukkit.events.FreezeEvent;
import fr.ulity.moderation.bukkit.events.InvSeeCancelEvent;
import fr.ulity.moderation.bukkit.events.MuteEvent;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.bukkit.Bukkit.getPluginManager;

public final class MainModBukkit extends JavaPlugin {
    public static MainModBukkit plugin;
    public static Server server;
    public static Config config;

    @Override
    public void onEnable() {
        plugin = this;
        server = getServer();

        Initializer init = new Initializer(this);
        init.requireVersion("2.3.1");
        init.reloadLang();
        init.checkUpdates(78787);

        Metrics metrics = new Metrics(this, 7588);

        if (init.ok){
            // register config field
            config = new Config("config", "addons/moderation");
            DefaultConfig.make();

            // register commands :
            new ClearChatCommand(Api.Bukkit.commandMap, this);
            new ChatCommand(Api.Bukkit.commandMap, this);
            new MuteCommand(Api.Bukkit.commandMap, this);
            new TempMuteCommand(Api.Bukkit.commandMap, this);
            new UnMuteCommand(Api.Bukkit.commandMap, this);
            new BanCommand(Api.Bukkit.commandMap, this);
            new TempBanCommand(Api.Bukkit.commandMap, this);
            new UnBanCommand(Api.Bukkit.commandMap, this);
            new VanishCommand(Api.Bukkit.commandMap, this);
            new StaffChatCommand(Api.Bukkit.commandMap, this);
            new KickCommand(Api.Bukkit.commandMap, this);
            new BanIpCommand(Api.Bukkit.commandMap, this);
            new UnBanIpCommand(Api.Bukkit.commandMap, this);
            new InvSeeCommand(Api.Bukkit.commandMap, this);
            new FreezeCommand(Api.Bukkit.commandMap, this);
            new UnFreezeCommand(Api.Bukkit.commandMap, this);

            // register/start modules :
            StartModule.start();


            // register events
            getPluginManager().registerEvents(new MuteEvent(), this);
            getPluginManager().registerEvents(new BanEvent(), this);
            getPluginManager().registerEvents(new InvSeeCancelEvent(), this);
            getPluginManager().registerEvents(new FreezeEvent(), this);

        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
