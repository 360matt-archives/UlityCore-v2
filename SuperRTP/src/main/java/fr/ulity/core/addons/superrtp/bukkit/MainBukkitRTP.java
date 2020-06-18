package fr.ulity.core.addons.superrtp.bukkit;

import fr.ulity.core.addons.superrtp.bukkit.commands.CommandRTP;
import fr.ulity.core.addons.superrtp.bukkit.events.InventoryEventRTP;
import fr.ulity.core.api.Api;
import fr.ulity.core.api.Config;
import fr.ulity.core.api.Initializer;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

import static org.bukkit.Bukkit.getPluginManager;

public final class MainBukkitRTP extends JavaPlugin {
    public static MainBukkitRTP plugin;
    public static Config config;
    public static HashMap<String, String> items = new HashMap<>();

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
        init.requireVersion("2.1.1");
        init.reloadLang();

        if (init.ok) {
            config = new Config("config", "super_rtp");
            ConfigCopy.setDefault();


            new CommandRTP(Api.Bukkit.commandMap, this);


            getPluginManager().registerEvents(new InventoryEventRTP(), this);



        }


    }


    @Override
    public void onDisable() {

    }

}