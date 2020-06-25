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

public class PayCommand extends CommandManager {
    public PayCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "pay");
        addDescription(Lang.get("commands.pay.description"));
        addUsage(Lang.get("commands.pay.usage"));
        addPermission("ulity.packutils.pay");

        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.get(sender, "global.player_only"));
            return true;
        }

        if (args.length == 2 && StringUtils.isNumeric(args[1])) {
            Player playerHandle = Bukkit.getPlayer(args[0]);
            if (playerHandle == null) {
                sender.sendMessage(Lang.get(sender, "global.invalid_player")
                        .replaceAll("%player%", args[0]));
            } else {
                if (new EconomyMethods().has(sender.getName(), Double.parseDouble(args[1]))) {

                    playerHandle.sendMessage(Lang.get(playerHandle, "commands.pay.expressions.received")
                            .replaceAll("%money%", (args[1]))
                            .replaceAll("%player%", (sender.getName())));

                    sender.sendMessage(Lang.get(sender, "commands.pay.expressions.result")
                            .replaceAll("%money%", (args[1]))
                            .replaceAll("%player%", (playerHandle.getName())));


                } else {
                    sender.sendMessage(Lang.get(sender, "commands.pay.expressions.not_enough_money")
                            .replaceAll("%money%", (args[1])));
                }
            }
            return true;
        }
        return false;
    }

}