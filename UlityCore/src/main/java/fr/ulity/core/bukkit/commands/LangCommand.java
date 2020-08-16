package fr.ulity.core.bukkit.commands;

import fr.ulity.core.api.Api;
import fr.ulity.core.api.bukkit.CommandManager;
import fr.ulity.core.api.bukkit.LangBukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class LangCommand extends CommandManager.Assisted {
    public LangCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "lang");
        addDescription(LangBukkit.get("plugin.commands.lang.description"));
        addUsage(LangBukkit.get("plugin.commands.lang.usage"));
        setAliases(Arrays.asList(LangBukkit.getStringArray("plugin.commands.lang.aliases")));

        registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1)
            LangBukkit.prepare("plugin.commands.lang.lang_required").sendPlayer(sender);
        else {
            Api.data.set("player." + sender.getName() + ".lang", args[0]);
            LangBukkit.prepare("plugin.commands.lang.lang_changed").sendPlayer(sender);
        }
    }
}
