package net.listenia.api.bukkit;

import net.listenia.api.commons.ListeniaAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.URISyntaxException;

public final class ListeniaBukkit extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            ListeniaAPI.initialize(this);
        } catch (LoginException | IOException | URISyntaxException e) {
            getLogger().severe("Impossible d'établir une connexion au bot Discord !");
            Bukkit.shutdown();
            e.printStackTrace();
        }


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
