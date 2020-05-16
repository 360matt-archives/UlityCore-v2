package fr.ulity.moderation.bukkit.commands;

import fr.ulity.core.api.Api;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import fr.ulity.moderation.bukkit.MainModBukkit;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class VanishCommand extends CommandManager {

    public VanishCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "vanish");
        addDescription(Lang.get("commands.vanish.description"));

        addUsage(Lang.get("commands.vanish.usage"));
        addPermission("ulity.mod.vanish");
        addPermission("ulity.mod.vanish.others");
        addPermission("ulity.mod.vanish.self");

        addOneTabbComplete(-1, "vanish");

        addAliases("v");

        registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player target = null;

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Lang.get(sender, "global.player_only"));
                return true;
            }
            else if (!sender.hasPermission("ulity.mod.vanish.self") || !sender.hasPermission("ulity.mod.vanish")) {
                sender.sendMessage(Lang.get(sender, "commands.vanish.expressions.no_perm_self"));
                return true;
            }
            else
                target = (Player) sender;
        }
        else if (args.length == 1) {
            target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage(Lang.get(sender, "global.invalid_player")
                        .replaceAll("%player%", args[0]));
                return true;
            }
            else if (!sender.hasPermission("ulity.mod.vanish.others") || !sender.hasPermission("ulity.mod.vanish")) {
                sender.sendMessage(Lang.get(sender, "commands.vanish.expressions.no_perm_other"));
                return true;
            }
        }

        if (!Api.temp.isSet(target.getName() + ".vanish")){
            Api.temp.set(target.getName() + ".vanish", true);

            for (Player onePlayer : Bukkit.getOnlinePlayers())
                if (!onePlayer.hasPermission("ulity.mod.vanish"))
                    onePlayer.hidePlayer(MainModBukkit.plugin, target);

            target.sendMessage(Lang.get(target, "commands.vanish.expressions.vanished_notification"));

            if (!target.getName().equals(sender.getName()))
                sender.sendMessage(Lang.get(sender, "commands.vanish.expressions.vanished_other")
                        .replaceAll("%player%", target.getName()));


        } else{
            Api.temp.delete(target.getName() + ".vanish");

            for (Player onePlayer : Bukkit.getOnlinePlayers())
                onePlayer.showPlayer(MainModBukkit.plugin, target);

            target.sendMessage(Lang.get(target, "commands.vanish.expressions.unvanished_notification"));

            if (!target.getName().equals(sender.getName()))
                sender.sendMessage(Lang.get(sender, "commands.vanish.expressions.unvanished_other")
                        .replaceAll("%player%", target.getName()));
        }

        return true;
    }


}
