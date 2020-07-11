package fr.ulity.moderation.bukkit.commands;

import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import fr.ulity.core.utils.Text;
import fr.ulity.moderation.bukkit.api.Ban;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Date;

public class BanIpCommand extends CommandManager.Assisted {

    public BanIpCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "banip");
        addPermission("ulity.mod.banip");
        addListTabbComplete(1, null, null, Lang.getStringArray("commands.ban.reasons_predefined"));
        registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            if (arg.requirePlayer(0)) {
                Player player = arg.getPlayer(0);

                if (player.hasPermission("ulity.mod")) {
                    Lang.prepare("commands.banip.expressions.cant_ban_staff_ip")
                            .variable("player", args[0])
                            .sendPlayer(sender);
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
                        Bukkit.broadcastMessage(
                                Lang.prepare("commands.banip.broadcast." + keyName)
                                        .variable("player", player.getName())
                                        .variable("staff", sender.getName())
                                        .variable("reason", reason)
                                        .getOutput()
                        );
                    } else
                        Lang.prepare("commands.banip.expressions.result")
                                .variable("player", player.getName())
                                .sendPlayer(sender);

                    arg.getPlayer(0).kickPlayer(
                            Lang.prepare("commands.banip.expressions.you_are_banned")
                                    .variable("staff", sender.getName())
                                    .variable("reason", reason)
                                    .getOutput(arg.getPlayer(0))
                    );
                }
            }
        } else
            setStatus(Status.SYNTAX);
    }
}
