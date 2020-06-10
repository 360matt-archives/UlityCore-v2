package fr.ulity.moderation.bukkit.commands;


import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import fr.ulity.core.utils.Text;
import fr.ulity.moderation.bukkit.api.Ban;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;
import java.util.Objects;

public class BanIpCommand extends CommandManager {

    public BanIpCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "banip");
        addDescription(Lang.get("commands.banip.description"));
        addUsage(Lang.get("commands.banip.usage"));
        addPermission("ulity.mod.banip");

        addOneTabbComplete(-1, "ulity.mod.banip", "banip");
        addListTabbComplete(1, null, null, Lang.getStringArray("commands.ban.reasons_predefined"));

        registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            Player player = Bukkit.getPlayer(args[0]);

            if (player != null){
                if (player.hasPermission("ulity.mod")){
                    sender.sendMessage(Lang.get(sender, "commands.banip.expressions.cant_ban_staff_ip")
                            .replaceAll("%player%", args[0]));
                } else {
                    String reason = (args.length >= 2) ? Text.fullColor(args, 1) : Lang.get("commands.banip.expressions.unknown_reason");
                    String ip = player.getAddress().getAddress().toString().replaceAll("/", "").replaceAll("\\.", "_");

                    Ban playerBan = new Ban("ip_" + ip);
                    playerBan.timestamp = new Date().getTime();
                    playerBan.reason = reason;
                    playerBan.expire = 0;
                    playerBan.responsable = sender.getName();
                    playerBan.ban();

                    if (Lang.getBoolean("commands.banip.broadcast.enabled")) {
                        String keyName = (args.length >= 2) ? "message" : "message_without_reason";
                        Bukkit.broadcastMessage(Lang.get("commands.banip.broadcast." + keyName)
                                .replaceAll("%player%", player.getName())
                                .replaceAll("%staff%", sender.getName())
                                .replaceAll("%reason%", reason));
                    } else
                        sender.sendMessage(Lang.get(sender, "commands.banip.expressions.result")
                                .replaceAll("%player%", player.getName()));

                    player.kickPlayer(Lang.get(player, "commands.banip.expressions.you_are_banned")
                            .replaceAll("%staff%", sender.getName())
                            .replaceAll("%reason%", reason));
                }
            }
            else{
                sender.sendMessage(Lang.get(sender, "global.invalid_player")
                        .replaceAll("%player%", args[0]));
            }

            return true;
        }
        return false;
    }


}
