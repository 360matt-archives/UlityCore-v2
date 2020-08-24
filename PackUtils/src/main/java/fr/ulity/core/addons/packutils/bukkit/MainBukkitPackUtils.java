package fr.ulity.core.addons.packutils.bukkit;

import fr.ulity.core.addons.packutils.bukkit.commands.economy.*;
import fr.ulity.core.addons.packutils.bukkit.commands.gamemode.*;
import fr.ulity.core.addons.packutils.bukkit.commands.players.*;
import fr.ulity.core.addons.packutils.bukkit.commands.teleports.*;
import fr.ulity.core.addons.packutils.bukkit.commands.time.DayCommand;
import fr.ulity.core.addons.packutils.bukkit.commands.time.NightCommand;
import fr.ulity.core.addons.packutils.bukkit.commands.weather.*;
import fr.ulity.core.addons.packutils.bukkit.methods.EconomyMethods;


import fr.ulity.core_v3.Core;
import fr.ulity.core_v3.modules.storage.ServerConfig;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class MainBukkitPackUtils extends JavaPlugin {
    public static MainBukkitPackUtils plugin;
    public static ServerConfig config;

    public static CommandEnabler enabler;

    @Override
    public void onEnable() {
        plugin = this;
        Core.initialize(this);

        Plugin vault = getServer().getPluginManager().getPlugin("Vault");
        getServer().getServicesManager().register(Economy.class, new EconomyMethods(), vault, ServicePriority.Highest);


        config = new ServerConfig("config");
        DefaultConfig.applique();

        enabler = new CommandEnabler();

            new GamemodeCommand();
            new GmcCommand();
            new GmsCommand();
            new GmaCommand();
            new GmpCommand();

            new FlyCommand();
            new TpCommand();
            new TpaCommand();
            new TpyesCommand();
            new TpnoCommand();
            new TopCommand();
            new BackCommand();
            new SetspawnCommand();
            new SpawnCommand();
            new SethomeCommand();
            new HomeCommand();
            new DelhomeCommand();

            new EcoCommand();
            new BalanceCommand();
            new PayCommand();

            new SunCommand();
            new RainCommand();
            new ThunderCommand();

            new DayCommand();
            new NightCommand();




    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
