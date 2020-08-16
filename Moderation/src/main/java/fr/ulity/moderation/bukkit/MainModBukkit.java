package fr.ulity.moderation.bukkit;


import fr.ulity.core.api.*;
import fr.ulity.core.api.bukkit.InitializerBukkit;
import fr.ulity.core.api.bukkit.MetricsBukkit;
import fr.ulity.moderation.bukkit.commands.*;
import fr.ulity.moderation.bukkit.events.BanEvent;
import fr.ulity.moderation.bukkit.events.FreezeEvent;
import fr.ulity.moderation.bukkit.events.InvSeeCancelEvent;
import fr.ulity.moderation.bukkit.events.MuteEvent;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;


import static org.bukkit.Bukkit.getPluginManager;

public final class MainModBukkit extends JavaPlugin {
    public static MainModBukkit plugin;
    public static Server server;
    public static Config config;

    @Override
    public void onEnable() {
        plugin = this;
        server = getServer();

        InitializerBukkit init = new InitializerBukkit(this);
        init.requireVersion("2.5");
        init.reloadLang();
        init.checkUpdates(78787);

        MetricsBukkit metrics = new MetricsBukkit(this, 7588);

        if (init.ok){
            // register config field
            config = new Config("config", "addons/moderation");
            DefaultConfig.make();

            // register commands :
            new ClearChatCommand(Api.getCommandMap(), this);
            new ChatCommand(Api.getCommandMap(), this);
            new MuteCommand(Api.getCommandMap(), this);
            new TempMuteCommand(Api.getCommandMap(), this);
            new UnMuteCommand(Api.getCommandMap(), this);
            new BanCommand(Api.getCommandMap(), this);
            new TempBanCommand(Api.getCommandMap(), this);
            new UnBanCommand(Api.getCommandMap(), this);
            new VanishCommand(Api.getCommandMap(), this);
            new StaffChatCommand(Api.getCommandMap(), this);
            new KickCommand(Api.getCommandMap(), this);
            new BanIpCommand(Api.getCommandMap(), this);
            new UnBanIpCommand(Api.getCommandMap(), this);
            new InvSeeCommand(Api.getCommandMap(), this);
            new FreezeCommand(Api.getCommandMap(), this);
            new UnFreezeCommand(Api.getCommandMap(), this);

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
