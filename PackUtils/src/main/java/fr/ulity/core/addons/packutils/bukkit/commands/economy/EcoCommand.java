package fr.ulity.core.addons.packutils.bukkit.commands.economy;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.EconomyMethods;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class EcoCommand extends CommandManager {
    public EcoCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "eco");
        addDescription(Lang.get("commands.eco.description"));
        addUsage(Lang.get("commands.eco.usage"));
        addPermission("ulity.packutils.eco");

        addArrayTabbComplete(0, null, new String[0], new String[]{"set", "add", "remove"});

        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("clear")) {
                EconomyMethods.money.clear();
                sender.sendMessage(Lang.get(sender, "commands.eco.expressions.all_accounts_cleared"));
            }
        } else if (args.length >= 2) {
            Player playerHandle = Bukkit.getPlayer(args[1]);
            if (playerHandle == null) {
                sender.sendMessage(Lang.get(sender, "global.invalid_player")
                        .replaceAll("%player%", args[1]));
                return true;
            }

            if (args[0].equalsIgnoreCase("set") && args.length == 3 && StringUtils.isNumeric(args[2])) {
                EconomyMethods.money.set("player." + playerHandle.getName(), Double.parseDouble(args[2]));
                sender.sendMessage(Lang.get(sender, "commands.eco.expressions.sold_set")
                        .replaceAll("%player%", playerHandle.getName())
                        .replaceAll("%money%", args[2]));
                return true;
            } else if (args[0].equalsIgnoreCase("add") && args.length == 3 && StringUtils.isNumeric(args[2])) {
                new EconomyMethods().withdrawPlayer(playerHandle.getName(), Double.parseDouble(args[2]));
                sender.sendMessage(Lang.get(sender, "commands.eco.expressions.sold_added")
                        .replaceAll("%player%", playerHandle.getName())
                        .replaceAll("%added%", args[2]));
                return true;
            } else if (args[0].equalsIgnoreCase("remove") && args.length == 3 && StringUtils.isNumeric(args[2])) {
                new EconomyMethods().depositPlayer(playerHandle.getName(), Double.parseDouble(args[2]));
                sender.sendMessage(Lang.get(sender, "commands.eco.expressions.sold_taked")
                        .replaceAll("%player%", playerHandle.getName())
                        .replaceAll("%taked%", args[2]));
                return true;
            }
        }

        return false;
    }


}