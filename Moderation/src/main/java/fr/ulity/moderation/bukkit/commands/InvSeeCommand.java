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

public class InvSeeCommand extends CommandManager.Assisted {

    public InvSeeCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "invsee");
        addPermission("ulity.mod.invsee");
        registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (requirePlayer()) {
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);

                if (arg.requirePlayer(0)) {
                    PlayerInventory inventory = target.getInventory();
                    ((Player) sender).openInventory(inventory);

                    Lang.prepare("commands.invsee.expressions.inventory_opened")
                            .variable("player", target.getName())
                            .sendPlayer(sender);
                }
            } else
                setStatus(Status.SYNTAX);
        }
    }
}