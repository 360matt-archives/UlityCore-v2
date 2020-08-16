package fr.ulity.core.addons.packutils.bukkit;

import fr.ulity.core.addons.packutils.bukkit.commands.economy.*;
import fr.ulity.core.addons.packutils.bukkit.commands.gamemode.*;
import fr.ulity.core.addons.packutils.bukkit.commands.players.*;
import fr.ulity.core.addons.packutils.bukkit.commands.teleports.*;
import fr.ulity.core.addons.packutils.bukkit.commands.time.DayCommand;
import fr.ulity.core.addons.packutils.bukkit.commands.time.NightCommand;
import fr.ulity.core.addons.packutils.bukkit.commands.weather.*;
import fr.ulity.core.addons.packutils.bukkit.methods.EconomyMethods;
import fr.ulity.core.api.Api;
import fr.ulity.core.api.Config;
import fr.ulity.core.api.bukkit.InitializerBukkit;
import fr.ulity.core.api.bukkit.MetricsBukkit;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class MainBukkitPackUtils extends JavaPlugin {
    public static MainBukkitPackUtils plugin;
    public static Config config;

    public static CommandEnabler enabler;

    @Override
    public void onEnable() {
        plugin = this;

        Plugin vault = getServer().getPluginManager().getPlugin("Vault");
        getServer().getServicesManager().register(Economy.class, new EconomyMethods(), vault, ServicePriority.Highest);

        InitializerBukkit init = new InitializerBukkit(this);
        init.requireVersion("2.5");
        init.checkUpdates(81641);
        init.reloadLang();

        if (init.ok) {
            MetricsBukkit metricsBukkit = new MetricsBukkit(this, 8230);
            metricsBukkit.addCustomChart(new MetricsBukkit.SimplePie("others_plugins", () -> Arrays.toString(getServer().getPluginManager().getPlugins())));

            config = new Config("config", "addons/PackUtils");
            DefaultConfig.applique();

            enabler = new CommandEnabler();

            new GamemodeCommand(Api.getCommandMap(), this);
            new GmcCommand(Api.getCommandMap(), this);
            new GmsCommand(Api.getCommandMap(), this);
            new GmaCommand(Api.getCommandMap(), this);
            new GmpCommand(Api.getCommandMap(), this);

            new FlyCommand(Api.getCommandMap(), this);
            new TpCommand(Api.getCommandMap(), this);
            new TpaCommand(Api.getCommandMap(), this);
            new TpyesCommand(Api.getCommandMap(), this);
            new TpnoCommand(Api.getCommandMap(), this);
            new TopCommand(Api.getCommandMap(), this);
            new BackCommand(Api.getCommandMap(), this);
            new SetspawnCommand(Api.getCommandMap(), this);
            new SpawnCommand(Api.getCommandMap(), this);
            new SethomeCommand(Api.getCommandMap(), this);
            new HomeCommand(Api.getCommandMap(), this);
            new DelhomeCommand(Api.getCommandMap(), this);

            new EcoCommand(Api.getCommandMap(), this);
            new BalanceCommand(Api.getCommandMap(), this);
            new PayCommand(Api.getCommandMap(), this);

            new SunCommand(Api.getCommandMap(), this);
            new RainCommand(Api.getCommandMap(), this);
            new ThunderCommand(Api.getCommandMap(), this);

            new DayCommand(Api.getCommandMap(), this);
            new NightCommand(Api.getCommandMap(), this);

        }


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
