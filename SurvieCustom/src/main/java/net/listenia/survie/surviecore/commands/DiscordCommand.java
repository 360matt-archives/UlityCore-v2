package net.listenia.survie.surviecore.commands;

import fr.ulity.core.api.CommandManager;
import net.listenia.survie.surviecore.SurvieCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class DiscordCommand extends CommandManager {
    public DiscordCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "discord");
        addDescription("§ePermet d'obtenir le lien d'invitation du Discord du serveur");
        addUsage("§eRécupérer l'invitation: §7/discord");

        registerCommand(commandMap);


    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("§eLe lien du Discord est: §6" + SurvieCore.config.getOrSetDefault("discord.invite", "discord.gg/"));
        return true;
    }

}