package fr.ulity.moderation.bukkit.commands;

import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import fr.ulity.core.utils.Text;
import fr.ulity.moderation.bukkit.api.Freeze;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;

public class FreezeCommand extends CommandManager {

    public FreezeCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "freeze");
        addDescription(Lang.get("commands.freeze.description"));
        addUsage(Lang.get("commands.freeze.usage"));
        addPermission("ulity.mod.freeze");

        addOneTabbComplete(-1, "ulity.mod.freeze", "freeze");
        addListTabbComplete(1, null, null, Lang.getStringArray("commands.ban.reasons_predefined"));

        registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            @SuppressWarnings("deprecation")
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null && target.hasPermission("ulity.mod")){
                sender.sendMessage(Lang.get(sender, "commands.freeze.expressions.cant_freeze_staff")
                        .replaceAll("%player%", args[0]));
                    return true;
            }

            String reason = (args.length >= 2) ? Text.fullColor(args, 1) : Lang.get("commands.freeze.expressions.unknown_reason");

            Freeze playerFreeze = new Freeze(args[0]);
            playerFreeze.reason = reason;
            playerFreeze.responsable = sender.getName();
            playerFreeze.timestamp = new Date().getTime();
            playerFreeze.freeze();

            sender.sendMessage(Lang.get(sender, "commands.freeze.expressions.player_freezed")
                    .replaceAll("%player%", args[0]));

            return true;
        }

        return false;
    }

}