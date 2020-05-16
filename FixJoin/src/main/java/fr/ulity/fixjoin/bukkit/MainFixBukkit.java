package fr.ulity.fixjoin.bukkit;

import fr.ulity.core.api.Config;
import fr.ulity.core.api.Initializer;
import fr.ulity.fixjoin.bukkit.event.onJoin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MainFixBukkit extends JavaPlugin {

    public static Config config;

    @Override
    public void onEnable() {
        Initializer init = new Initializer(this);
        init.requireVersion("2.0.3");
        init.reloadLang();

        if (init.ok) {
            config = new Config("config", "fixjoin");

            Bukkit.getPluginManager().registerEvents(new onJoin(), this);
        }


        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
