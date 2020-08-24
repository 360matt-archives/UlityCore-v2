package fr.ulity.core.addons.superrtp.bukkit;

import fr.ulity.core.addons.superrtp.bukkit.commands.CommandRTP;
import fr.ulity.core.addons.superrtp.bukkit.events.InventoryEventRTP;
import fr.ulity.core.addons.superrtp.bukkit.events.InvincibleRTP;
import fr.ulity.core_v3.Core;
import fr.ulity.core_v3.modules.loaders.BukkitLoader;
import fr.ulity.core_v3.modules.storage.ServerConfig;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;


import java.util.HashMap;

import static org.bukkit.Bukkit.getPluginManager;

public final class MainBukkitRTP extends BukkitLoader {
    public static MainBukkitRTP plugin;
    public static ServerConfig config;
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
        Core.initialize(this);

        config = new ServerConfig("config");
        ConfigCopy.setDefault();

        new CommandRTP();

        getPluginManager().registerEvents(new InventoryEventRTP(), this);
        getPluginManager().registerEvents(new InvincibleRTP(), this);

    }

    @Override
    public void onDisable() { }
}