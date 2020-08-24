package fr.ulity.moderation.bukkit.cmd;

import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import fr.ulity.core_v3.utils.Text;
import fr.ulity.moderation.api.sanctions.BanUser;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class BanCmd extends CommandBukkit {

    public BanCmd() {
        super("ban");
        setPermission("ulity.mod.ban");
        addListTabbComplete(1, null, null, Lang.getArray("commands.ban.reasons_predefined"));

    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 1) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);


            if (player.hasPlayedBefore() && Bukkit.getPlayer(args[0]).hasPermission("ulity.mod")){
                Lang.prepare("commands.ban.expressions.cant_ban_staff")
                        .variable("player", args[0])
                        .sendPlayer(sender);
            } else {
                String reason = (args.length >= 2)
                        ? new Text(args).setColored().setBeginging(1).outputString()
                        : Lang.get("commands.ban.expressions.unknown_reason");

                BanUser playerBan = new BanUser(player.getName());
                playerBan.when = new Date();
                playerBan.reason = reason;
                playerBan.expire = new Date(0);
                playerBan.staff = sender.getName();
                playerBan.ban();

                if (Lang.getBoolean("commands.ban.broadcast.enabled")) {
                    String keyName = (args.length >= 2) ? "message" : "message_without_reason";
                    Bukkit.broadcastMessage(
                            Lang.prepare("commands.ban.broadcast." + keyName)
                                    .variable("player", player.getName())
                                    .variable("staff", sender.getName())
                                    .variable("reason", reason)
                                    .getOutput()
                    );
                }
                else
                    Lang.prepare("commands.ban.expressions.result")
                            .variable("player", player.getName())
                            .sendPlayer(sender);

                if (player.isOnline())
                    arg.getPlayer(0).kickPlayer(
                            Lang.prepare("commands.ban.expressions.you_are_banned")
                                    .variable("staff", sender.getName())
                                    .variable("reason", reason)
                                    .getOutput(arg.getPlayer(0))
                    );
            }



        } else
            setStatus(Status.SYNTAX);
    }
}
