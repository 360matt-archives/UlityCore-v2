package fr.ulity.moderation.bukkit.cmd;

import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import fr.ulity.core_v3.utils.Text;

import fr.ulity.moderation.api.sanctions.BanIP;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class BanipCmd extends CommandBukkit {
    public BanipCmd() {
        super("banip");
        setPermission("ulity.mod.banip");
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 1) {
            if (arg.requirePlayer(0)) {
                Player player = arg.getPlayer(0);

                if (player.hasPermission("ulity.mod")) {
                    Lang.prepare("commands.banip.expressions.cant_ban_staff_ip")
                            .variable("player", args[0])
                            .sendPlayer(sender);
                } else {
                    String reason = (args.length >= 2)
                            ? new Text(args).setColored().setBeginging(1).outputString()
                            : Lang.get("commands.banip.expressions.unknown_reason");

                    String ip = player.getAddress().getAddress().getHostAddress()
                            .replaceAll("/", "")
                            .replaceAll("\\.", "_");

                    BanIP banIP = new BanIP(ip);
                    banIP.when = new Date();
                    banIP.reason = reason;
                    banIP.expire = new Date();
                    banIP.staff = sender.getName();
                    banIP.ban();

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
