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
import fr.ulity.core.api.Initializer;
import fr.ulity.core.api.Metrics;

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

        Initializer init = new Initializer(this);
        init.requireVersion("2.4");
        init.checkUpdates(81641);
        init.reloadLang();

        if (init.ok) {
            Metrics metrics = new Metrics(this, 8230);
            metrics.addCustomChart(new Metrics.SimplePie("others_plugins", () -> Arrays.toString(getServer().getPluginManager().getPlugins())));

            config = new Config("config", "addons/PackUtils");
            DefaultConfig.applique();

            enabler = new CommandEnabler();

            new GamemodeCommand(Api.Bukkit.commandMap, this);
            new GmcCommand(Api.Bukkit.commandMap, this);
            new GmsCommand(Api.Bukkit.commandMap, this);
            new GmaCommand(Api.Bukkit.commandMap, this);
            new GmpCommand(Api.Bukkit.commandMap, this);

            new FlyCommand(Api.Bukkit.commandMap, this);
            new TpCommand(Api.Bukkit.commandMap, this);
            new TpaCommand(Api.Bukkit.commandMap, this);
            new TpyesCommand(Api.Bukkit.commandMap, this);
            new TpnoCommand(Api.Bukkit.commandMap, this);
            new TopCommand(Api.Bukkit.commandMap, this);
            new BackCommand(Api.Bukkit.commandMap, this);
            new SetspawnCommand(Api.Bukkit.commandMap, this);
            new SpawnCommand(Api.Bukkit.commandMap, this);
            new SethomeCommand(Api.Bukkit.commandMap, this);
            new HomeCommand(Api.Bukkit.commandMap, this);
            new DelhomeCommand(Api.Bukkit.commandMap, this);

            new EcoCommand(Api.Bukkit.commandMap, this);
            new BalanceCommand(Api.Bukkit.commandMap, this);
            new PayCommand(Api.Bukkit.commandMap, this);

            new SunCommand(Api.Bukkit.commandMap, this);
            new RainCommand(Api.Bukkit.commandMap, this);
            new ThunderCommand(Api.Bukkit.commandMap, this);

            new DayCommand(Api.Bukkit.commandMap, this);
            new NightCommand(Api.Bukkit.commandMap, this);

        }


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
