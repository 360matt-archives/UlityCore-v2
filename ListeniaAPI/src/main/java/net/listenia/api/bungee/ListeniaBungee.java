package net.listenia.api.bungee;

import fr.ulity.core.api.bungee.InitializerBungee;
import net.listenia.api.commons.ListeniaAPI;
import net.md_5.bungee.api.plugin.Plugin;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.URISyntaxException;

public final class ListeniaBungee extends Plugin {

    @Override
    public void onEnable() {
        InitializerBungee init = new InitializerBungee(this);
        if (init.ok) {
            try {
                ListeniaAPI.initialize(this);
            } catch (LoginException | IOException | URISyntaxException e) {
                getLogger().severe("Impossible d'établir une connexion au bot Discord !");
                getProxy().stop();
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
