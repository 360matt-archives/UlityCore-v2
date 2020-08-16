package fr.ulity.core.bukkit;

import fr.ulity.core.api.Api;
import fr.ulity.core.api.bukkit.InitializerBukkit;
import fr.ulity.core.api.bukkit.MetricsBukkit;
import fr.ulity.core.bukkit.commands.*;
import fr.ulity.core.bukkit.loaders.IsUpToDate;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

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

        MetricsBukkit metricsBukkit = new MetricsBukkit(this, 6520);
        metricsBukkit.addCustomChart(new MetricsBukkit.SimplePie("others_plugins", () -> Arrays.toString(getServer().getPluginManager().getPlugins())));

        if (Api.ok) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                System.out.println(" §c____ ___.__  .__  __          _________                       \n" +
                        "§c|    |   \\  | |__|/  |_ ___.__.\\_   ___ \\  ___________   ____  \n" +
                        "§c|    |   /  | |  \\   __<   |  |/    \\  \\/ /  _ \\_  __ \\_/ __ \\ \n" +
                        "§c|    |  /|  |_|  ||  |  \\___  |\\     \\___(  <_> )  | \\/\\  ___/ \n" +
                        "§c|______/ |____/__||__|  / ____| \\______  /\\____/|__|    \\___  >\n" +
                        "§c                        \\/             \\/                   \\/ \n" +
                        "\n" +
                        "§e>_   §bCore - Library - API \n" +
                        "§e>_   §6" + InitializerBukkit.lesPlugins.size() + " §bextensions loaded");
            });


            new UlityCoreCommand(Api.getCommandMap(), this);
            new LangCommand(Api.getCommandMap(), this);

            // getServer().getPluginManager().registerEvents(new X(), this);

        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
