package fr.ulity.moderation.bukkit.commands;

import fr.ulity.core.api.bukkit.CommandManager;
import fr.ulity.core.api.bukkit.LangBukkit;
import fr.ulity.moderation.bukkit.api.Ban;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class UnBanCommand extends CommandManager.Assisted {
    public UnBanCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "unban");
        addPermission("ulity.mod.unban");
        addListTabbComplete(1, null, null, LangBukkit.getStringArray("commands.unban.reasons_predefined"));
        registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            @SuppressWarnings("deprecation")
            OfflinePlayer player = Bukkit.getOfflinePlayer(arg.get(0));

            Ban playerBan = new Ban(player.getName());
            String result;
            if (playerBan.isBan()){
                playerBan.unban();
                result = "unbanned";
            } else
                result = "is_not_banned";

            LangBukkit.prepare("commands.unban.expressions." + result)
                    .variable("player", player.getName())
                    .sendPlayer(sender);
        } else
            setStatus(Status.SYNTAX);
    }
}
