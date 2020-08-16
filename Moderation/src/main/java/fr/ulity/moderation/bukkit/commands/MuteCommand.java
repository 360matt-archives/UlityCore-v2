package fr.ulity.moderation.bukkit.commands;

import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.bukkit.LangBukkit;
import fr.ulity.core.utils.TextV2;
import fr.ulity.moderation.bukkit.api.Mute;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class MuteCommand extends CommandManager.Assisted {
    public MuteCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "mute");
        addPermission("ulity.mod.mute");
        addListTabbComplete(1, null, null, LangBukkit.getStringArray("commands.mute.reasons_predefined"));

        registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            if (arg.isPlayer(0) && arg.getPlayer(0).hasPermission("ulity.mod")) {
                LangBukkit.prepare("commands.mute.expressions.cant_ban_staff")
                        .variable("player", arg.get(0))
                        .sendPlayer(sender);
                setStatus(Assisted.Status.STOP);
            }

            if (status.equals(Status.SUCCESS)) {
                String reason = (args.length >= 2)
                        ? new TextV2(args).setColored().setBeginging(1).outputString()
                        : LangBukkit.get("commands.mute.expressions.unknown_reason");

                Mute playerMute = new Mute(arg.get(0));
                playerMute.reason = reason;
                playerMute.expire = 0;
                playerMute.responsable = sender.getName();
                playerMute.mute();

                if (LangBukkit.getBoolean("commands.mute.broadcast.enabled")) {
                    String keyName = (args.length >= 2) ? "message" : "message_without_reason";
                    Bukkit.broadcastMessage(
                            LangBukkit.prepare("commands.mute.broadcast." + keyName)
                            .variable("player", arg.get(0))
                            .variable("staff", sender.getName())
                            .variable("reason", reason)
                            .getOutput()
                    );
                } else {
                    @SuppressWarnings("deprecation")
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
                    if (player.isOnline())
                        LangBukkit.prepare("commands.mute.expressions.you_are_muted")
                                .variable("staff", sender.getName())
                                .variable("reason", reason)
                                .sendPlayer(arg.getPlayer(0));

                    LangBukkit.prepare("commands.mute.expressions.result")
                            .variable("player", player.getName())
                            .sendPlayer(sender);
                }
            }
        } else
            setStatus(Status.SYNTAX);
    }
}
