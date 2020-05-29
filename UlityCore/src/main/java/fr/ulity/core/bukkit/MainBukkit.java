package fr.ulity.core.bukkit;

import fr.ulity.core.api.Api;
import fr.ulity.core.api.Metrics;
import fr.ulity.core.bukkit.commands.*;
import fr.ulity.core.bukkit.loaders.IsUpToDate;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Arrays;

public final class MainBukkit extends JavaPlugin {
    public static MainBukkit plugin;
    public static CommandMap commandMap;

    @Override
    public void onEnable() {
        System.setProperty("file.encoding","UTF-8");

        plugin = this;
        Api.initialize(this);

        new IsUpToDate(this, 75201).noticeUpdate();

        Metrics metrics = new Metrics(this, 6520);
        metrics.addCustomChart(new Metrics.SimplePie("others_plugins", () -> Arrays.toString(getServer().getPluginManager().getPlugins())));

        try {
            Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            commandMap = (CommandMap) f.get(Bukkit.getServer());

            new UlityCoreCommand(commandMap, this);
            new LangCommand(commandMap, this);


            // getServer().getPluginManager().registerEvents(new X(), this);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            getLogger().severe(e.getLocalizedMessage());
            e.printStackTrace();
            this.getPluginLoader().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
