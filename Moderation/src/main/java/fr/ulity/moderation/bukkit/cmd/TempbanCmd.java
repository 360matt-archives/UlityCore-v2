package fr.ulity.moderation.bukkit.cmd;

import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import fr.ulity.core_v3.utils.Text;
import fr.ulity.core_v3.utils.Time;
import fr.ulity.moderation.api.sanctions.BanUser;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class TempbanCmd extends CommandBukkit {
    public TempbanCmd () {
        super("tempban");
        setPermission("ulity.mod.tempban");
        addListTabbComplete(2, null, null, Lang.getArray("commands.tempban.reasons_predefined"));
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 2) {
            if (arg.isPlayer(0) && arg.getPlayer(0).hasPermission("ulity.mod")) {
                Lang.prepare("commands.tempban.expressions.cant_ban_staff")
                        .variable("player", arg.get(0))
                        .sendPlayer(sender);
                setStatus(Status.STOP);
            }

            if (status.equals(Status.SUCCESS)) {
                String reason = (args.length >= 3)
                        ? new Text(args).setColored().setBeginging(2).outputString()
                        : Lang.get("commands.tempban.expressions.unknown_reason");

                Time time = new Time(args[1]);

                BanUser playerBan = new BanUser(arg.get(0));
                playerBan.when = new Date();
                playerBan.reason = reason;
                playerBan.expire = new Date(new Date().getTime() + time.milliseconds);
                playerBan.staff = sender.getName();
                playerBan.ban();

                if (Lang.getBoolean("commands.tempban.broadcast.enabled")) {
                    String keyName = (args.length >= 3) ? "message" : "message_without_reason";
                    Bukkit.broadcastMessage(
                            Lang.prepare("commands.tempban.broadcast." + keyName)
                                    .variable("player", arg.get(0))
                                    .variable("staff", sender.getName())
                                    .variable("reason", reason)
                                    .variable("time", time.text)
                                    .getOutput()
                    );
                }
                else {
                    @SuppressWarnings("deprecation")
                    OfflinePlayer player = Bukkit.getOfflinePlayer(arg.get(0));
                    if (player.isOnline())
                        arg.getPlayer(0).kickPlayer(
                                Lang.prepare("commands.tempban.expressions.you_are_banned")
                                        .variable("staff", sender.getName())
                                        .variable("reason", reason)
                                        .variable("timeLeft", time.text)
                                        .getOutput(arg.getPlayer(0))
                        );

                    Lang.prepare("commands.tempban.expressions.result")
                            .variable("player", player.getName())
                            .sendPlayer(sender);
                }
            }
        } else
            setStatus(Status.SYNTAX);
    }
}
