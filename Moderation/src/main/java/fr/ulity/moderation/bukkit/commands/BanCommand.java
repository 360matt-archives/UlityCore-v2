package fr.ulity.moderation.bukkit.commands;

import fr.ulity.core.api.bukkit.CommandManager;
import fr.ulity.core.api.bukkit.LangBukkit;
import fr.ulity.core.utils.TextV2;
import fr.ulity.moderation.bukkit.api.Ban;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;

public class BanCommand extends CommandManager.Assisted {

    public BanCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "ban");
        addPermission("ulity.mod.ban");
        addListTabbComplete(1, null, null, LangBukkit.getStringArray("commands.ban.reasons_predefined"));
        registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            @SuppressWarnings("deprecation")
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);

            if (player.hasPlayedBefore() && Bukkit.getPlayer(args[0]).hasPermission("ulity.mod")){
                LangBukkit.prepare("commands.ban.expressions.cant_ban_staff")
                        .variable("player", args[0])
                        .sendPlayer(sender);
            } else {
                String reason = (args.length >= 2)
                        ? new TextV2(args).setColored().setBeginging(1).outputString()
                        : LangBukkit.get("commands.ban.expressions.unknown_reason");

                Ban playerBan = new Ban(player.getName());
                playerBan.timestamp = new Date().getTime();
                playerBan.reason = reason;
                playerBan.expire = 0;
                playerBan.responsable = sender.getName();
                playerBan.ban();

                if (LangBukkit.getBoolean("commands.ban.broadcast.enabled")) {
                    String keyName = (args.length >= 2) ? "message" : "message_without_reason";
                    Bukkit.broadcastMessage(
                            LangBukkit.prepare("commands.ban.broadcast." + keyName)
                            .variable("player", player.getName())
                            .variable("staff", sender.getName())
                            .variable("reason", reason)
                            .getOutput()
                    );
                }
                else
                    LangBukkit.prepare("commands.ban.expressions.result")
                            .variable("player", player.getName())
                            .sendPlayer(sender);

                if (player.isOnline())
                    arg.getPlayer(0).kickPlayer(
                            LangBukkit.prepare("commands.ban.expressions.you_are_banned")
                                    .variable("staff", sender.getName())
                                    .variable("reason", reason)
                                    .getOutput(arg.getPlayer(0))
                    );
            }

        } else
            setStatus(Status.SYNTAX);
    }
}
