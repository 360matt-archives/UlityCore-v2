package fr.ulity.moderation.bukkit.commands;

import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.bukkit.LangBukkit;
import fr.ulity.moderation.bukkit.api.Ban;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class UnBanIpCommand extends CommandManager.Assisted {
    public UnBanIpCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "unbanip");
        addPermission("ulity.mod.unbanip");
        registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            Ban playerBanIP = new Ban("ip_" + arg.get(0).replaceAll("\\.", "_"));
            // les points sont remplacés par des underscores pour le stockage

            String result;
            if (playerBanIP.isBan()){
                playerBanIP.unban();
                result = "unbanned";
            } else
                result = "is_not_banned";

            LangBukkit.prepare("commands.unbanip.expressions." + result)
                    .variable("ip", arg.get(0))
                    .sendPlayer(sender);
        } else
            setStatus(Status.SYNTAX);
    }
}