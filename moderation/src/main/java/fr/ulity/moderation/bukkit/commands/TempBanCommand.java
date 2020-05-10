package fr.ulity.moderation.bukkit.commands;


import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import fr.ulity.core.utils.Text;
import fr.ulity.core.utils.Time;
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

public class TempBanCommand extends CommandManager {

    public TempBanCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "tempban");
        addDescription(Lang.get("commands.tempban.description"));
        addUsage(Lang.get("commands.tempban.usage"));
        addPermission("ulity.mod.tempban");

        addOneTabbComplete(-1, "ulity.mod.tempban", "tempban");
        addListTabbComplete(2, null, null, Lang.getStringArray("commands.tempban.reasons_predefined"));

        registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 2) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            if (player == null)
                sender.sendMessage(Lang.get("global.invalid_player").replaceAll("%player%", args[0]));
            else {
                String reason = (args.length >= 3) ? Text.fullColor(args, 2) : Lang.get("commands.tempban.expressions.unknown_reason");
                Time time = new Time(args[1]);

                Ban playerBan = new Ban(player.getName());
                playerBan.timestamp = new Date().getTime();
                playerBan.reason = reason;
                playerBan.expire = new Date().getTime() + time.milliseconds;
                playerBan.responsable = sender.getName();
                playerBan.ban();

                if (Lang.getBoolean("commands.tempban.broadcast.enabled")) {
                    String keyName = (args.length >= 3) ? "message" : "message_without_reason";
                    Bukkit.broadcastMessage(Lang.get("commands.tempban.broadcast." + keyName)
                            .replaceAll("%player%", player.getName())
                            .replaceAll("%staff%", sender.getName())
                            .replaceAll("%reason%", reason)
                            .replaceAll("%time%", time.text));
                }

                if (player.isOnline())
                    Bukkit.getPlayer(args[0]).kickPlayer(Lang.get(player, "commands.tempban.expressions.you_are_banned")
                        .replaceAll("%staff%", sender.getName())
                        .replaceAll("%reason%", reason)
                        .replaceAll("%timeLeft%", time.text));

            }
            return true;
        }
        return false;
    }


}
