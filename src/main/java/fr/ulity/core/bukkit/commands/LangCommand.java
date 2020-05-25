package fr.ulity.core.bukkit.commands;

import fr.ulity.core.api.Api;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class LangCommand extends CommandManager {
    public LangCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "lang");
        addDescription(Lang.get("commands.tempmute.description"));
        addUsage(Lang.get("commands.tempmute.usage"));
        setAliases(Arrays.asList(Lang.getStringArray("plugin.commands.lang.aliases")));

        addOneTabbComplete(-1, null, "lang");
        registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length != 1)
            sender.sendMessage(Lang.get(sender, "plugin.commands.lang.lang_required"));
        else
            if (Files.exists(Paths.get(Api.full_prefix + "/languages/" + args[0] + ".yml"))){
                Api.data.set("player." + sender.getName() + ".lang", args[0]);
                sender.sendMessage(Lang.get(sender, "plugin.commands.lang.lang_changed"));
            }
            else
                sender.sendMessage(Lang.get(sender, "plugin.commands.lang.lang_unknown")
                        .replaceAll("%lang%", args[0]));

        return true;
    }

}
