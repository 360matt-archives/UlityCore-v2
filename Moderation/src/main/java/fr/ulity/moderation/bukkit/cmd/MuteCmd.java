package fr.ulity.moderation.bukkit.cmd;

import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import fr.ulity.core_v3.utils.Text;
import fr.ulity.moderation.api.sanctions.MuteUser;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class MuteCmd extends CommandBukkit {
    public MuteCmd () {
        super("mute");
        setPermission("ulity.mod.mute");
        addListTabbComplete(1, null, null, Lang.getArray("commands.mute.reasons_predefined"));
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 1) {
            if (arg.isPlayer(0) && arg.getPlayer(0).hasPermission("ulity.mod")) {
                Lang.prepare("commands.mute.expressions.cant_ban_staff")
                        .variable("player", arg.get(0))
                        .sendPlayer(sender);
                setStatus(Status.STOP);
            }

            if (status.equals(Status.SUCCESS)) {
                String reason = (args.length >= 2)
                        ? new Text(args).setColored().setBeginging(1).outputString()
                        : Lang.get("commands.mute.expressions.unknown_reason");

                MuteUser playerMute = new MuteUser(arg.get(0));
                playerMute.reason = reason;
                playerMute.expire = new Date();
                playerMute.staff = sender.getName();
                playerMute.mute();

                if (Lang.getBoolean("commands.mute.broadcast.enabled")) {
                    String keyName = (args.length >= 2) ? "message" : "message_without_reason";
                    Bukkit.broadcastMessage(
                            Lang.prepare("commands.mute.broadcast." + keyName)
                                    .variable("player", arg.get(0))
                                    .variable("staff", sender.getName())
                                    .variable("reason", reason)
                                    .getOutput()
                    );
                } else {
                    @SuppressWarnings("deprecation")
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
                    if (player.isOnline())
                        Lang.prepare("commands.mute.expressions.you_are_muted")
                                .variable("staff", sender.getName())
                                .variable("reason", reason)
                                .sendPlayer(arg.getPlayer(0));

                    Lang.prepare("commands.mute.expressions.result")
                            .variable("player", player.getName())
                            .sendPlayer(sender);
                }
            }
        } else
            setStatus(Status.SYNTAX);
    }
}
