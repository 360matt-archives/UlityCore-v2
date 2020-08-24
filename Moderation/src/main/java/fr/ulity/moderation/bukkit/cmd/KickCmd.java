package fr.ulity.moderation.bukkit.cmd;

import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import fr.ulity.core_v3.utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class KickCmd extends CommandBukkit {
    public KickCmd () {
        super("kick");
        setPermission("ulity.mod.kick");
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 1) {
            if (arg.requirePlayer(0)) {
                Player player = arg.getPlayer(0);

                if (player.hasPermission("ulity.mod")){
                    Lang.prepare("commands.kick.expressions.cant_kick_staff")
                            .variable("player", player.getName())
                            .sendPlayer(sender);
                } else{
                    String reason = (args.length >= 2)
                            ? new Text(args).setColored().setBeginging(1).outputString()
                            : Lang.get("commands.kick.expressions.unknown_reason");

                    if (Lang.getBoolean("commands.ban.broadcast.enabled")) {
                        String keyName = (args.length >= 2) ? "message" : "message_without_reason";
                        Bukkit.broadcastMessage(
                                Lang.prepare("commands.kick.broadcast." + keyName)
                                        .variable("player", player.getName())
                                        .variable("staff", sender.getName())
                                        .variable("reason", reason)
                                        .getOutput()
                        );
                    } else
                        Lang.prepare("commands.kick.expressions.result")
                                .variable("player", player.getName())
                                .sendPlayer(sender);

                    player.kickPlayer(
                            Lang.prepare("commands.kick.expressions.you_are_kicked")
                                    .variable("staff", sender.getName())
                                    .variable("reason", reason)
                                    .getOutput(player)
                    );
                }
            }
        } else
            setStatus(Status.SYNTAX);
    }
}
