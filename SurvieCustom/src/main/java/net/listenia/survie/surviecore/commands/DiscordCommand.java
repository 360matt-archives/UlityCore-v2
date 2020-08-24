package net.listenia.survie.surviecore.commands;

import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import net.listenia.survie.surviecore.SurvieCore;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;

public class DiscordCommand extends CommandBukkit {
    public DiscordCommand() {
        super("discord");
        setDescription("§ePermet d'obtenir le lien d'invitation du Discord du serveur");
        setUsage("§eRécupérer l'invitation: §7/discord");
    }

    @Override
    public void exec (@NotNull CommandSender sender, @NotNull String s, @NotNull String[] strings) {
        sender.sendMessage("§eLe lien du Discord est: §6" + SurvieCore.config.getOrSetDefault("discord.invite", "discord.gg/"));
    }


}