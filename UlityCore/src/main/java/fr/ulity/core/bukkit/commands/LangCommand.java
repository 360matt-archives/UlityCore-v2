package fr.ulity.core.bukkit.commands;

import fr.ulity.core.api.Api;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class LangCommand extends CommandManager.Assisted {
    public LangCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "lang");
        addDescription(Lang.get("plugin.commands.lang.description"));
        addUsage(Lang.get("plugin.commands.lang.usage"));
        setAliases(Arrays.asList(Lang.getStringArray("plugin.commands.lang.aliases")));

        registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1)
            Lang.prepare("plugin.commands.lang.lang_required").sendPlayer(sender);
        else {
            Api.data.set("player." + sender.getName() + ".lang", args[0]);
            Lang.prepare("plugin.commands.lang.lang_changed").sendPlayer(sender);
        }
    }
}
