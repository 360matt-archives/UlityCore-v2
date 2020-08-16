package net.listenia.api.commons;

import de.leonhard.storage.Config;
import fr.ulity.bot.MainDiscordApi;
import fr.ulity.core.api.Api;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class ListeniaAPI {
    public static File path;
    public static Config config;



    public static void initialize (Object pluginObj) throws LoginException, IOException, URISyntaxException {
        File configFig = null;
        if (Api.type.equals(Api.PluginType.BUKKIT))
            configFig = new File(((org.bukkit.plugin.java.JavaPlugin) pluginObj).getDataFolder().getPath() + "/config.yml");
        else if (Api.type.equals(Api.PluginType.BUNGEE))
            configFig = new File(((net.md_5.bungee.api.plugin.Plugin) pluginObj).getDataFolder().getPath() + "/config.yml");

        assert configFig != null;
        config = new Config(configFig);

        path = new File(config.getOrSetDefault("api.path", "/home/API/"));
        path.mkdirs();

        startBot();

    }

    public static void startBot () throws LoginException, IOException, URISyntaxException {
        MainDiscordApi.start(new File(path.getPath() + "/DiscordBot/"));
        fr.ulity.bot.api.Config config = MainDiscordApi.config;

        config.setDefault("channels.reports", 0);

    }



}
