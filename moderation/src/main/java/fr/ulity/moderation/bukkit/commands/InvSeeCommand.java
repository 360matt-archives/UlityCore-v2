package fr.ulity.moderation.bukkit.commands;

import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public class InvSeeCommand extends CommandManager {

    public InvSeeCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "invsee");
        addDescription(Lang.get("commands.invsee.description"));
        addUsage(Lang.get("commands.invsee.usage"));
        addPermission("ulity.mod.invsee");

        addOneTabbComplete(-1, "ulity.mod.invsee", "invsee");

        registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(Lang.get("global.player_only"));
            return true;
        }
        else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null){
                sender.sendMessage(Lang.get(sender, "global.invalid_player")
                        .replaceAll("%player%", args[0]));
            }
            else{
                PlayerInventory inventory = target.getInventory();
                ((Player) sender).openInventory(inventory);

                sender.sendMessage(Lang.get(sender, "commands.invsee.expressions.inventory_opened")
                        .replaceAll("%player%", target.getName()));
            }
            return true;
        }
        return false;
    }

}