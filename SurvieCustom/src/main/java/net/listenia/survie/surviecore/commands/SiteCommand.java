package net.listenia.survie.surviecore.commands;

import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import net.listenia.survie.surviecore.SurvieCore;
import org.bukkit.command.CommandSender;

public class SiteCommand extends CommandBukkit {
    public SiteCommand() {
        super("site");
        setDescription("§ePermet d'obtenir le lien du site du serveur");
        setUsage("§eRécupérer le lien: §7/site");
    }

    @Override
    public void exec (CommandSender sender, String label, String[] args) {
        sender.sendMessage("§eLe lien du site est: §6" + SurvieCore.config.getOrSetDefault("site.lien", "site.net"));
    }

}