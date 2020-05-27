package fr.ulity.moderation.bukkit.commands;

import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import fr.ulity.moderation.bukkit.api.Freeze;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class UnFreezeCommand extends CommandManager {

    public UnFreezeCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "unfreeze");
        addDescription(Lang.get("commands.unfreeze.description"));
        addUsage(Lang.get("commands.unfreeze.usage"));
        addPermission("ulity.mod.unfreeze");

        addOneTabbComplete(-1, "ulity.mod.unfreeze", "unfreeze");

        registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
                Freeze playerFreeze = new Freeze(args[0]);
                if (playerFreeze.isFreeze()){
                    playerFreeze.unfreeze();

                    sender.sendMessage(Lang.get(sender, "commands.unfreeze.expressions.player_unfreezed")
                            .replaceAll("%player%", args[0]));

                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null)
                        target.sendMessage(Lang.get(target, "commands.unfreeze.expressions.notification"));
                }
                else{
                    sender.sendMessage(Lang.get(sender, "commands.unfreeze.expressions.is_not_freezed")
                            .replaceAll("%player%", args[0]));
                }

                return true;
            }

        return false;
    }

}