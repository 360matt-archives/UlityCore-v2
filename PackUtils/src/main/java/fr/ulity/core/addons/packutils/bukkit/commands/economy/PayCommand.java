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

public class PayCommand extends CommandManager.Assisted {
    public PayCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "pay");
        addPermission("ulity.packutils.pay");
        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (requirePlayer()) {
            if (arg.requirePlayerNoSelf(0)) {
                if (arg.requireNumber(1)) {
                    Player playerHandle = arg.getPlayer(0);
                    if (new EconomyMethods().has(sender.getName(), Double.parseDouble(args[1]))) {
                        Lang.prepare("commands.pay.expressions.received")
                                .variable("money", args[1])
                                .variable("player", sender.getName())
                                .sendPlayer(playerHandle);

                        Lang.prepare("commands.pay.expressions.result")
                                .variable("money", args[1])
                                .variable("player", playerHandle.getName())
                                .sendPlayer(sender);
                    } else {
                        Lang.prepare("commands.pay.expressions.not_enough_money")
                                .variable("money", args[1])
                                .sendPlayer(sender);
                    }
                }
            }
        }
    }

}