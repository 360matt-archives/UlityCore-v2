package fr.ulity.moderation.bukkit.commands;


import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import fr.ulity.core.utils.Text;
import fr.ulity.moderation.bukkit.api.Ban;
import fr.ulity.moderation.bukkit.api.Mute;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Date;

public class UnBanCommand extends CommandManager {

    public UnBanCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "unban");
        addDescription(Lang.get("commands.unban.description"));
        addUsage(Lang.get("commands.unban.usage"));
        addPermission("ulity.mod.unban");

        addOneTabbComplete(-1, "ulity.mod.unban", "unban");
        addListTabbComplete(1, null, null, Lang.getStringArray("commands.unban.reasons_predefined"));

        registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            @SuppressWarnings("deprecation")
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);

            Ban playerMute = new Ban(player.getName());

            if (playerMute.isBan()){
                playerMute.unban();
                sender.sendMessage(Lang.get(sender, "commands.unban.expressions.unbanned")
                        .replaceAll("%player%", player.getName()));
            } else
                sender.sendMessage(Lang.get(sender, "commands.unban.expressions.is_not_banned")
                        .replaceAll("%player%", player.getName()));

            return true;
        }
        return false;
    }


}
