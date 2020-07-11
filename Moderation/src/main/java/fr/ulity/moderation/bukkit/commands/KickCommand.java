package fr.ulity.moderation.bukkit.commands;


import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import fr.ulity.core.utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class KickCommand extends CommandManager.Assisted {
    public KickCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "kick");
        addPermission("ulity.mod.kick");
        addOneTabbComplete(-1, "ulity.mod.kick", "kick");
        addListTabbComplete(1, null, null, Lang.getStringArray("commands.kick.reasons_predefined"));
        registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            if (arg.requirePlayer(0)) {
                Player player = arg.getPlayer(0);

                if (player.hasPermission("ulity.mod")){
                    Lang.prepare("commands.kick.expressions.cant_kick_staff")
                            .variable("player", player.getName())
                            .sendPlayer(sender);
                } else{
                    String reason = (args.length >= 2) ? Text.fullColor(args, 1) : Lang.get("commands.kick.expressions.unknown_reason");

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
