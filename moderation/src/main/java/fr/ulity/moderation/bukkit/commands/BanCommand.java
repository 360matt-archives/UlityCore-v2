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

public class BanCommand extends CommandManager {

    public BanCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "ban");
        addDescription(Lang.get("commands.ban.description"));
        addUsage(Lang.get("commands.ban.usage"));
        addPermission("ulity.mod.ban");

        addOneTabbComplete(-1, "ulity.mod.ban", "ban");
        addListTabbComplete(1, null, null, Lang.getStringArray("commands.ban.reasons_predefined"));

        registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            if (player == null)
                sender.sendMessage(Lang.get("global.invalid_player").replaceAll("%player%", args[0]));
            else {
                String reason = (args.length >= 2) ? Text.fullColor(args, 1) : Lang.get("commands.ban.expressions.unknown_reason");

                Ban playerBan = new Ban(player.getName());
                playerBan.timestamp = new Date().getTime();
                playerBan.reason = reason;
                playerBan.expire = 0;
                playerBan.responsable = sender.getName();
                playerBan.ban();

                if (Lang.getBoolean("commands.ban.broadcast.enabled")) {
                    String keyName = (args.length >= 2) ? "message" : "message_without_reason";
                    Bukkit.broadcastMessage(Lang.get("commands.ban.broadcast." + keyName)
                            .replaceAll("%player%", player.getName())
                            .replaceAll("%staff%", sender.getName())
                            .replaceAll("%reason%", reason));
                }

                if (player.isOnline())
                    Bukkit.getPlayer(args[0]).kickPlayer(Lang.get(player, "commands.ban.expressions.you_are_banned")
                        .replaceAll("%staff%", sender.getName())
                        .replaceAll("%reason%", reason));

            }
            return true;
        }
        return false;
    }


}
