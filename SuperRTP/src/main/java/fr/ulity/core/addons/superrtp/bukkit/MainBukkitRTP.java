package fr.ulity.core.addons.superrtp.bukkit;

import fr.ulity.core.addons.superrtp.bukkit.commands.CommandRTP;
import fr.ulity.core.addons.superrtp.bukkit.events.InventoryEventRTP;
import fr.ulity.core.addons.superrtp.bukkit.events.InvincibleRTP;
import fr.ulity.core.api.Api;
import fr.ulity.core.api.Config;
import fr.ulity.core.api.Initializer;
import fr.ulity.core.api.Metrics;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;

import static org.bukkit.Bukkit.getPluginManager;

public final class MainBukkitRTP extends JavaPlugin {
    public static MainBukkitRTP plugin;
    public static Config config;
    public static HashMap<String, String> items = new HashMap<>();

    public static HashMap<String, HashMap<String, Object>> invincible = new HashMap<>();

    public static final class ObtainEco {
        public Economy eco;
        public boolean available;

        public ObtainEco () {
            available = false;
        }

        public ObtainEco (Economy ecoProvider) {
            eco = ecoProvider;
            available = true;
        }
    }

    public static ObtainEco getEco () {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp != null)
                return new ObtainEco(rsp.getProvider());
        }
        return new ObtainEco();
    }

    @Override
    public void onEnable() {
        plugin = this;

        Initializer init = new Initializer(this);
        init.requireVersion("2.3.1");
        init.checkUpdates(80372);
        init.reloadLang();

        Metrics metrics = new Metrics(this, 7891);
        metrics.addCustomChart(new Metrics.SimplePie("others_plugins", () -> Arrays.toString(getServer().getPluginManager().getPlugins())));

        if (init.ok) {
            config = new Config("config", "addons/superRTP");
            ConfigCopy.setDefault();


            new CommandRTP(Api.Bukkit.commandMap, this);

            getPluginManager().registerEvents(new InventoryEventRTP(), this);
            getPluginManager().registerEvents(new InvincibleRTP(), this);
        }
    }

    @Override
    public void onDisable() { }
}