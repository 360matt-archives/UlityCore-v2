package net.listenia.survie.surviecore.commands;

import fr.ulity.core.api.bukkit.CommandManager;
import net.listenia.survie.surviecore.SurvieCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class SiteCommand extends CommandManager {
    public SiteCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "site");
        addDescription("§ePermet d'obtenir le lien du site du serveur");
        addUsage("§eRécupérer le lien: §7/site");

        registerCommand(commandMap);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("§eLe lien du site est: §6" + SurvieCore.config.getOrSetDefault("site.lien", "site.net"));
        return true;
    }

}