package fr.ulity.moderation.bukkit.commands;

import fr.ulity.core.api.bukkit.CommandManager;

import fr.ulity.core.api.bukkit.LangBukkit;
import fr.ulity.core.utils.TextV2;
import fr.ulity.core.api.bukkit.TimeBukkit;
import fr.ulity.moderation.bukkit.api.Mute;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;

public class TempMuteCommand extends CommandManager.Assisted {

    public TempMuteCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "tempmute");
        addPermission("ulity.mod.tempmute");
        addListTabbComplete(2, null, null, LangBukkit.getStringArray("commands.tempmute.reasons_predefined"));
        registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 2) {
            if (arg.isPlayer(0) && arg.getPlayer(0).hasPermission("ulity.mod")) {
                LangBukkit.prepare("commands.tempmute.expressions.cant_ban_staff")
                        .variable("player", arg.get(0))
                        .sendPlayer(sender);
                setStatus(Status.STOP);
            }

            if (status.equals(Status.SUCCESS)) {
                String reason = (args.length >= 3)
                        ? new TextV2(args).setColored().setBeginging(2).outputString()
                        : LangBukkit.get("commands.mute.expressions.unknown_reason");

                TimeBukkit time = new TimeBukkit(args[1]);

                Mute playerMute = new Mute(arg.get(0));
                playerMute.reason = reason;
                playerMute.expire = new Date().getTime() + time.milliseconds;
                playerMute.responsable = sender.getName();
                playerMute.mute();

                if (LangBukkit.getBoolean("commands.tempmute.broadcast.enabled")) {
                    String keyName = (args.length >= 3) ? "message" : "message_without_reason";
                    Bukkit.broadcastMessage(
                            LangBukkit.prepare("commands.tempmute.broadcast." + keyName)
                                .variable("player", arg.get(0))
                                .variable("staff", sender.getName())
                                .variable("reason", reason)
                                .variable("time", time.text)
                                .getOutput()
                    );
                } else {
                    @SuppressWarnings("deprecation")
                    OfflinePlayer player = Bukkit.getOfflinePlayer(arg.get(0));
                    if (player.isOnline())
                        LangBukkit.prepare("commands.tempmute.expressions.you_are_muted")
                                .variable("staff", sender.getName())
                                .variable("reason", reason)
                                .variable("time", time.text)
                                .sendPlayer(arg.getPlayer(0));

                    LangBukkit.prepare("commands.tempmute.expressions.result")
                            .variable("player", player.getName())
                            .sendPlayer(sender);
                }
            }
        } else
            setStatus(Status.SYNTAX);
    }
}
