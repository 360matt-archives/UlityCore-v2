package fr.ulity.core.addons.packutils.bukkit.commands.economy;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.EconomyMethods;
import fr.ulity.core.api.bukkit.CommandManager;
import fr.ulity.core.api.bukkit.LangBukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BalanceCommand extends CommandManager.Assisted {
    public BalanceCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "balance");
        addPermission("ulity.packutils.balance");

        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (requirePlayer()) {
                LangBukkit.prepare("commands.balance.expressions.your_sold")
                        .variable("money", String.valueOf(new EconomyMethods().getBalance(sender.getName())))
                        .sendPlayer(sender);
            }
        } else if (args.length == 1) {
            if (arg.requirePlayer(0)) {
                Player playerHandle = arg.getPlayer(0);
                LangBukkit.prepare("commands.balance.expressions.sold_of_x")
                        .variable("player", playerHandle.getName())
                        .variable("money", String.valueOf(new EconomyMethods().getBalance(playerHandle.getName())))
                        .sendPlayer(sender);
            }
        } else
            setStatus(Status.SYNTAX);
    }
}