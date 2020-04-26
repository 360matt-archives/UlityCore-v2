package fr.ulity.core.bukkit;

import fr.ulity.core.api.Api;
import fr.ulity.core.bukkit.loaders.IsUpToDate;
import fr.ulity.core.bukkit.commands.UlityCoreCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public final class MainBukkit extends JavaPlugin {
    public static MainBukkit plugin;

    @Override
    public void onEnable() {
        plugin = this;

        Api.initialize(this);
        new IsUpToDate(this, 75201).noticeUpdate();

        try {
            Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            CommandMap commandMap = (CommandMap) f.get(Bukkit.getServer());

            new UlityCoreCommand(commandMap, this);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            getLogger().severe(e.getLocalizedMessage());
            e.printStackTrace();
            this.getPluginLoader().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
