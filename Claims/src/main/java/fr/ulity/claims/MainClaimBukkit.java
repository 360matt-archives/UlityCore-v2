package fr.ulity.claims;

import fr.ulity.claims.commands.ZoneCommand;
import fr.ulity.claims.events.FirstLevel;
import fr.ulity.core.api.Api;
import fr.ulity.core.api.Config;
import fr.ulity.core.api.Data;
import fr.ulity.core.api.Initializer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;

public final class MainClaimBukkit extends JavaPlugin {
    public static MainClaimBukkit plugin;
    public static Server server;
    public static Config config;
    public static Data data;

    public static HashMap<String, Boolean> worldBorderShowed = new HashMap<>();

    @Override
    public void onEnable() {


        plugin = this;
        server = getServer();

        Initializer init = new Initializer(this);
        init.requireVersion("2.0");
        init.reloadLang();

        config = new Config("config", "claims");
        data = new Data("data", "claims");

        config.setDefault("claims.max_owns", (long) 5);
        config.setDefault("claims.build_without_claim", false);
        config.setDefault("global.worlds", Collections.singletonList("world"));

        config.setDefault("extra.tnt", false);
        config.setDefault("extra.creeper", false);



        // enregistrons les commandes
        new ZoneCommand(Api.Bukkit.commandMap, this);

        // puis les events
        getServer().getPluginManager().registerEvents(new FirstLevel(), this);

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
