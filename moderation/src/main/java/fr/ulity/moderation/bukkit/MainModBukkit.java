package fr.ulity.moderation.bukkit;

import fr.ulity.core.api.Api;
import fr.ulity.core.api.Initializer;
import fr.ulity.core.api.Metrics;
import fr.ulity.moderation.bukkit.commands.*;
import fr.ulity.moderation.bukkit.events.BanEvent;
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

        Initializer init = new Initializer(this);
        init.requireVersion("2.1");
        init.reloadLang();
        init.checkUpdates(78787);

        Metrics metrics = new Metrics(this, 7588);
        

        if (init.ok){
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

            // register/start modules :
            StartModule.start();


            // register events
            getPluginManager().registerEvents(new MuteEvent(), this);
            getPluginManager().registerEvents(new BanEvent(), this);

        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
