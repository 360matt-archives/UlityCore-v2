package fr.ulity.core.addons.packutils.bukkit.commands.economy;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.EconomyMethods;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BalanceCommand extends CommandManager {
    public BalanceCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "balance");
        addDescription(Lang.get("commands.balance.description"));
        addUsage(Lang.get("commands.balance.usage"));
        addPermission("ulity.packutils.balance");

        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Lang.get(sender, "global.player_only"));
                return true;
            }

            sender.sendMessage(Lang.get(sender, "commands.balance.expressions.your_sold")
                    .replaceAll("%money%", String.valueOf(new EconomyMethods().getBalance(sender.getName()))));
        } else if (args.length == 1) {
            Player playerHandle = Bukkit.getPlayer(args[0]);
            if (playerHandle == null) {
                sender.sendMessage(Lang.get(sender, "global.invalid_player")
                        .replaceAll("%player%", args[0]));
                return true;
            }

            sender.sendMessage(Lang.get(sender, "commands.balance.expressions.sold_of_x")
                    .replaceAll("%player%", playerHandle.getName())
                    .replaceAll("%money%", String.valueOf(new EconomyMethods().getBalance(playerHandle.getName()))));
            return true;
        }

        return false;
    }

}