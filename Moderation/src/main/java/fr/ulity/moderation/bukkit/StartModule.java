package fr.ulity.moderation.bukkit;

import fr.ulity.core.api.bukkit.LangBukkit;
import fr.ulity.moderation.bukkit.events.AntiInsultEvent;
import org.bukkit.event.HandlerList;

import static org.bukkit.Bukkit.getPluginManager;

public class StartModule {

    public static void start () {
        if (LangBukkit.getBoolean("module.anti_insult.enabled"))
            getPluginManager().registerEvents(new AntiInsultEvent(), MainModBukkit.plugin);
        else
            HandlerList.unregisterAll(new AntiInsultEvent());

    }

}
