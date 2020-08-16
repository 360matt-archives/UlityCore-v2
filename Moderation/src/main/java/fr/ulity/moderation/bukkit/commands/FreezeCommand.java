package fr.ulity.moderation.bukkit.commands;

import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.bukkit.LangBukkit;
import fr.ulity.core.utils.TextV2;
import fr.ulity.moderation.bukkit.api.Freeze;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Date;

public class FreezeCommand extends CommandManager.Assisted {

    public FreezeCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "freeze");
        addPermission("ulity.mod.freeze");
        addListTabbComplete(1, null, null, LangBukkit.getStringArray("commands.ban.reasons_predefined"));
        registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            if (arg.requirePlayer(0)) {
                Player target = arg.getPlayer(0);
                if (target.hasPermission("ulity.mod")){
                    LangBukkit.prepare("commands.freeze.expressions.cant_freeze_staff")
                            .variable("player", target.getName())
                            .sendPlayer(sender);
                } else {
                    String reason = (args.length >= 2)
                            ? new TextV2(args).setColored().setBeginging(1).outputString()
                            : LangBukkit.get("commands.freeze.expressions.unknown_reason");

                    Freeze playerFreeze = new Freeze(args[0]);
                    playerFreeze.reason = reason;
                    playerFreeze.responsable = sender.getName();
                    playerFreeze.timestamp = new Date().getTime();
                    playerFreeze.freeze();

                    LangBukkit.prepare("commands.freeze.expressions.player_freezed")
                            .variable("player", target.getName())
                            .sendPlayer(sender);
                }
            }
        } else
            setStatus(Status.SYNTAX);
    }
}