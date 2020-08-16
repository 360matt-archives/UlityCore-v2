package fr.ulity.moderation.bukkit.commands;


import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.bukkit.LangBukkit;
import fr.ulity.core.utils.TextV2;
import fr.ulity.core.utils.Time;
import fr.ulity.moderation.bukkit.api.Ban;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;

public class TempBanCommand extends CommandManager.Assisted {

    public TempBanCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "tempban");
        addPermission("ulity.mod.tempban");
        addListTabbComplete(2, null, null, LangBukkit.getStringArray("commands.tempban.reasons_predefined"));
        registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 2) {
            if (arg.isPlayer(0) && arg.getPlayer(0).hasPermission("ulity.mod")) {
                LangBukkit.prepare("commands.tempban.expressions.cant_ban_staff")
                        .variable("player", arg.get(0))
                        .sendPlayer(sender);
                setStatus(Assisted.Status.STOP);
            }

            if (status.equals(Status.SUCCESS)) {
                String reason = (args.length >= 3)
                        ? new TextV2(args).setColored().setBeginging(2).outputString()
                        : LangBukkit.get("commands.tempban.expressions.unknown_reason");

                Time time = new Time(args[1]);

                Ban playerBan = new Ban(arg.get(0));
                playerBan.timestamp = new Date().getTime();
                playerBan.reason = reason;
                playerBan.expire = new Date().getTime() + time.milliseconds;
                playerBan.responsable = sender.getName();
                playerBan.ban();

                if (LangBukkit.getBoolean("commands.tempban.broadcast.enabled")) {
                    String keyName = (args.length >= 3) ? "message" : "message_without_reason";
                    Bukkit.broadcastMessage(
                            LangBukkit.prepare("commands.tempban.broadcast." + keyName)
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
                                LangBukkit.prepare("commands.tempban.expressions.you_are_banned")
                                        .variable("staff", sender.getName())
                                        .variable("reason", reason)
                                        .variable("timeLeft", time.text)
                                        .getOutput(arg.getPlayer(0))
                        );

                    LangBukkit.prepare("commands.tempban.expressions.result")
                            .variable("player", player.getName())
                            .sendPlayer(sender);
                }
            }
        } else
            setStatus(Status.SYNTAX);
    }
}
