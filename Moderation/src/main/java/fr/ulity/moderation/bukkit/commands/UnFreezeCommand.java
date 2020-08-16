package fr.ulity.moderation.bukkit.commands;

import fr.ulity.core.api.bukkit.CommandManager;
import fr.ulity.core.api.bukkit.LangBukkit;
import fr.ulity.moderation.bukkit.api.Freeze;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class UnFreezeCommand extends CommandManager.Assisted {
    public UnFreezeCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "unfreeze");
        addPermission("ulity.mod.unfreeze");
        addOneTabbComplete(-1, "ulity.mod.unfreeze", "unfreeze");
        registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
                Freeze playerFreeze = new Freeze(args[0]);
                if (playerFreeze.isFreeze()){
                    playerFreeze.unfreeze();

                    LangBukkit.prepare("commands.unfreeze.expressions.player_unfreezed")
                            .variable("player", args[0])
                            .sendPlayer(sender);

                    if (arg.isPlayer(0)) // if is online
                        LangBukkit.prepare("commands.unfreeze.expressions.notification").sendPlayer(arg.getPlayer(0));
                }
                else
                    LangBukkit.prepare("commands.unfreeze.expressions.is_not_freezed")
                            .variable("player", args[0])
                            .sendPlayer(sender);
            } else
                setStatus(Status.SYNTAX);
    }
}